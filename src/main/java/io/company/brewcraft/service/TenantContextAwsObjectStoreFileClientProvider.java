package io.company.brewcraft.service;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.s3.AmazonS3;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.IaasRepositoryProvider;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;
import io.company.brewcraft.security.idp.AwsFactory;

public class TenantContextAwsObjectStoreFileClientProvider implements IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> {
    // Because the IaasAuthorization expires in 1 hour so the existing client becomes useless.
    public static final Duration DURATION_REMOVE_UNUSED_CLIENTS = Duration.ofHours(1);

    private String region;
    private TenantContextIaasAuthorizationFetch authFetcher;
    private LoadingCache<GetAmazonS3ClientArgs, IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> > cache;
    private TenantContextIaasObjectStoreNameProvider bucketNameProvider;

    public TenantContextAwsObjectStoreFileClientProvider(String region, TenantContextIaasObjectStoreNameProvider bucketNameProvider, TenantContextIaasAuthorizationFetch authFetcher, AwsFactory awsFactory, Long getPresignUrlDuration) {
        this.region = region;
        this.authFetcher = authFetcher;
        this.bucketNameProvider = bucketNameProvider;

        this.cache = CacheBuilder.newBuilder()
                                 .expireAfterWrite(DURATION_REMOVE_UNUSED_CLIENTS)
                                 .build(new CacheLoader<GetAmazonS3ClientArgs, IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> >(){
                                    @Override
                                    public IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile>  load(GetAmazonS3ClientArgs args) throws Exception {
                                        AmazonS3 s3Client = awsFactory.s3Client(args.region, args.accessKeyId, args.accessSecretKey, args.sessionToken);
                                        IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> client = new AwsS3FileClient(s3Client, args.bucketName, LocalDateTimeMapper.INSTANCE, getPresignUrlDuration);

                                        return new SequentialExecutor<>(client);
                                    }
                                 });
    }

    @Override
    public IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile>  getIaasRepository() {
        IaasAuthorization authorization = authFetcher.fetch();

        GetAmazonS3ClientArgs args = new GetAmazonS3ClientArgs(this.region, bucketNameProvider.getObjectStoreName(), authorization.getAccessKeyId(), authorization.getAccessSecretKey(), authorization.getSessionToken());

        try {
            return this.cache.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class GetAmazonS3ClientArgs extends BaseModel {
    String region;
    String bucketName;
    String accessKeyId;
    String accessSecretKey;
    String sessionToken;

    public GetAmazonS3ClientArgs(String region, String bucketName, String accessKeyId, String accessSecretKey, String sessionToken) {
        this.region = region;
        this.bucketName = bucketName;
        this.accessKeyId = accessKeyId;
        this.accessSecretKey = accessSecretKey;
        this.sessionToken = sessionToken;
    }
}