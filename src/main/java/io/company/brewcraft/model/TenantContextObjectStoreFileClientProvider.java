package io.company.brewcraft.model;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.s3.AmazonS3;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.service.AwsS3FileClient;
import io.company.brewcraft.service.IaasClient;
import io.company.brewcraft.service.LocalDateTimeMapper;
import io.company.brewcraft.service.TenantContextIaasObjectStoreNameProvider;
import io.company.brewcraft.service.TenantContextIaasAuthorizationFetch;

public class TenantContextObjectStoreFileClientProvider implements IaasObjectStoreFileClientProvider {
    // Because the IaasAuthorization expires in 1 hour so the existing client becomes useless.
    public static final Duration DURATION_REMOVE_UNUSED_CLIENTS = Duration.ofHours(1);

    private String region;
    private TenantContextIaasAuthorizationFetch authFetcher;
    private LoadingCache<GetAmazonS3ClientArgs, IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> > cache;
    private TenantContextIaasObjectStoreNameProvider bucketNameProvider;

    public TenantContextObjectStoreFileClientProvider(String region, TenantContextIaasObjectStoreNameProvider bucketNameProvider, TenantContextIaasAuthorizationFetch authFetcher, AwsFactory awsFactory, Long getPresignUrlDuration) {
        this.region = region;
        this.authFetcher = authFetcher;
        this.bucketNameProvider = bucketNameProvider;

        this.cache = CacheBuilder.newBuilder()
                                 .expireAfterWrite(DURATION_REMOVE_UNUSED_CLIENTS)
                                 .build(new CacheLoader<GetAmazonS3ClientArgs, IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> >(){
                                    @Override
                                    public IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile>  load(GetAmazonS3ClientArgs args) throws Exception {
                                        AmazonS3 s3Client = awsFactory.s3Client(args.region, args.accessKey, args.accessSecret, args.sessionToken);
                                        return new AwsS3FileClient(s3Client, args.bucketName, LocalDateTimeMapper.INSTANCE, getPresignUrlDuration); // TODO: Pass in duration from env config
                                    }
                                 });
    }

    @Override
    public IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile>  getClient() {
        IaasAuthorization authorization = authFetcher.fetch();

        GetAmazonS3ClientArgs args = new GetAmazonS3ClientArgs(this.region, bucketNameProvider.getObjectStoreName(), authorization.getAccessKey(), authorization.getAccessSecret(), authorization.getSessionToken());

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
    String accessKey;
    String accessSecret;
    String sessionToken;

    public GetAmazonS3ClientArgs(String region, String bucketName, String accessKey, String accessSecret, String sessionToken) {
        this.region = region;
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.sessionToken = sessionToken;
    }
}