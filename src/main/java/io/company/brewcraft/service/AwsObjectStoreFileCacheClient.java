package io.company.brewcraft.service;

import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import com.amazonaws.HttpMethod;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.model.BaseModel;

public class AwsObjectStoreFileCacheClient implements AwsObjectStoreFileSystemClient {
    public static final Duration EXPIRATION_DURATION_CLEAR_PRESIGNED_URLS = Duration.ofHours(1); // Expires 1 hour after last access 

    private LoadingCache<PresignArgs, URL> presign;
    
    public AwsObjectStoreFileCacheClient(AwsObjectStoreFileSystemClient delegate) {
        this.presign = CacheBuilder.newBuilder()
                                   .expireAfterAccess(EXPIRATION_DURATION_CLEAR_PRESIGNED_URLS)
                                   .build(new CacheLoader<PresignArgs, URL>() {
                                        @Override
                                        public URL load(PresignArgs args) throws Exception {
                                            return delegate.presign(args.fileKey, args.expiration, args.httpMethod);
                                        }
                                   });
    }

    @Override
    public URL presign(String fileKey, Date expiration, HttpMethod httpMethod) {
        PresignArgs args = new PresignArgs(fileKey, expiration, httpMethod);
        try {
            return this.presign.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class PresignArgs extends BaseModel {
    public PresignArgs(String fileKey, Date expiration, HttpMethod httpMethod) {
        this.fileKey = fileKey;
        this.expiration = expiration;
        this.httpMethod = httpMethod;
    }

    String bucketName;
    String fileKey;
    Date expiration;
    HttpMethod httpMethod;
}
