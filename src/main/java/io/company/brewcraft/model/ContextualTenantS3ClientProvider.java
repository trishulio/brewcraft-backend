package io.company.brewcraft.model;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.s3.AmazonS3;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.security.idp.AwsFactory;
import io.company.brewcraft.service.TenantIaasAuthorizationFetch;

public class ContextualTenantS3ClientProvider implements AmazonS3Provider {
    public static final Duration DURATION_REMOVE_UNUSED_CLIENTS = Duration.ofDays(1);

    private String region;
    private TenantIaasAuthorizationFetch authFetcher;
    private LoadingCache<GetAmazonS3ClientArgs, AmazonS3> cache;

    public ContextualTenantS3ClientProvider(String region, TenantIaasAuthorizationFetch authFetcher, AwsFactory awsFactory) {
        this.region = region;
        this.authFetcher = authFetcher;

        this.cache = CacheBuilder.newBuilder()
                                 .expireAfterWrite(DURATION_REMOVE_UNUSED_CLIENTS)
                                 .build(new CacheLoader<GetAmazonS3ClientArgs, AmazonS3>(){
                                    @Override
                                    public AmazonS3 load(GetAmazonS3ClientArgs args) throws Exception {
                                        return awsFactory.s3Client(args.region, args.accessKey, args.accessSecret);
                                    }
                                 });
    }

    @Override
    public AmazonS3 getAmazonS3Client() {
        IaasAuthorization authorization = authFetcher.fetch();
        GetAmazonS3ClientArgs args = new GetAmazonS3ClientArgs(this.region, authorization.getAccessKey(), authorization.getAccessSecret(), authorization.getSessionToken());

        try {
            return this.cache.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class GetAmazonS3ClientArgs extends BaseModel {
    String region;
    String accessKey;
    String accessSecret;
    String sessionToken;

    public GetAmazonS3ClientArgs(String region, String accessKey, String accessSecret, String sessionToken) {
        this.region = region;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.sessionToken = sessionToken;
    }
}