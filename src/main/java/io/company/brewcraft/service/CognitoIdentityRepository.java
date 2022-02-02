package io.company.brewcraft.service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CognitoIdentityRepository {
    private CognitoIdentityClient cognitoIdClient;
    private String identityIdCached;
    
    private LoadingCache<String, Credentials> credentialsCache;
    
    public CognitoIdentityRepository(CognitoIdentityClient cognitoIdClient) {
        this.cognitoIdClient = cognitoIdClient;
        this.identityIdCached = null;
        
        this.credentialsCache = CacheBuilder.newBuilder()
                                            .expireAfterWrite(Duration.ofHours(1))
                                            .build(new CacheLoader<String, Credentials>(){
                                                    @Override
                                                    public Credentials load(String idToken) throws Exception {
                                                        return cognitoIdClient.getCredentialsForIdentity(getIdentityId(idToken), idToken);
                                                    }
                                                }
                                            );
    }

    public Credentials getCredentials(String idToken) {
        try {
            return this.credentialsCache.get(idToken);
        } catch (ExecutionException e) {
            throw new RuntimeException(String.format("Failed to fetch the credentials because: %s", e.getMessage()), e);
        }
    }
    
    private String getIdentityId(String idToken) {
        if (this.identityIdCached == null) {
            this.identityIdCached = this.cognitoIdClient.getId(idToken);
        } 

        return this.identityIdCached;
    }
}
