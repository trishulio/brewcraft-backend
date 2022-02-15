package io.company.brewcraft.model;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.s3.AmazonS3;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.service.AwsObjectStoreFileCacheClient;
import io.company.brewcraft.service.AwsObjectStoreFileRemoteClient;
import io.company.brewcraft.service.AwsObjectStoreFileSystemClient;
import io.company.brewcraft.service.ObjectStoreNameProvider;
import io.company.brewcraft.service.TenantIaasAuthorizationFetch;

public class TenantContextObjectStoreFileClientProvider implements AwsObjectStoreFileClientProvider {
    public static final Duration DURATION_REMOVE_UNUSED_CLIENTS = Duration.ofHours(1); // Because the IaasAuthorization expires in 1 hour so the existing client becomes useless.

    private String region;
    private TenantIaasAuthorizationFetch authFetcher;
    private LoadingCache<GetAmazonS3ClientArgs, AwsObjectStoreFileSystemClient> cache;
    private ObjectStoreNameProvider bucketNameProvider;

    public TenantContextObjectStoreFileClientProvider(String region, ObjectStoreNameProvider bucketNameProvider, TenantIaasAuthorizationFetch authFetcher, AwsFactory awsFactory) {
        this.region = region;
        this.authFetcher = authFetcher;
        this.bucketNameProvider = bucketNameProvider;

        this.cache = CacheBuilder.newBuilder()
                                 .expireAfterWrite(DURATION_REMOVE_UNUSED_CLIENTS)
                                 .build(new CacheLoader<GetAmazonS3ClientArgs, AwsObjectStoreFileSystemClient>(){
                                    @Override
                                    public AwsObjectStoreFileSystemClient load(GetAmazonS3ClientArgs args) throws Exception {
                                        AmazonS3 s3Client = awsFactory.s3Client(args.region, args.accessKey, args.accessSecret, args.sessionToken);
                                        AwsObjectStoreFileSystemClient remoteClient = new AwsObjectStoreFileRemoteClient(s3Client, args.bucketName);
                                        AwsObjectStoreFileSystemClient cachedClient = new AwsObjectStoreFileCacheClient(remoteClient);

                                        return cachedClient;
                                    }
                                 });
    }

    @Override
    public AwsObjectStoreFileSystemClient getClient() {
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