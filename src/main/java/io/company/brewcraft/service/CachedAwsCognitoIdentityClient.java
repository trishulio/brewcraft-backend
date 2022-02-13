package io.company.brewcraft.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.company.brewcraft.model.BaseModel;

public class CachedAwsCognitoIdentityClient implements AwsCognitoIdentityClient {
    private static final int HOURS_GET_ID_CACHE_EXPIRATION = 1;
    private static final int HOURS_GET_CREDENTIALS_EXPIRATION = -10; // TODO: see the token expiration duration and put the correct value here
    
    private LoadingCache<GetIdentityPoolsArgs, List<IdentityPoolShortDescription>> getIdentityPools;
    private LoadingCache<GetIdentityIdArgs, String> getIdentityId;    
    private LoadingCache<GetCredentialsForIdentityIdArgs, Credentials> getCredentialsForIdentity;
    
    
    public CachedAwsCognitoIdentityClient(AwsCognitoIdentityClient cognitoIdClient) {
        this.getIdentityPools = CacheBuilder.newBuilder()
                                            .build(new CacheLoader<GetIdentityPoolsArgs, List<IdentityPoolShortDescription>>() {
                                              @Override
                                              public List<IdentityPoolShortDescription> load(GetIdentityPoolsArgs key) throws Exception {
                                                  return cognitoIdClient.getIdentityPools(key.pageSize);
                                              }
                                            });

        this.getIdentityId = CacheBuilder.newBuilder()
                                      .expireAfterAccess(Duration.ofHours(HOURS_GET_ID_CACHE_EXPIRATION))
                                      .build(new CacheLoader<GetIdentityIdArgs, String>() {
                                        @Override
                                        public String load(GetIdentityIdArgs key) throws Exception {
                                            return cognitoIdClient.getIdentityId(key.identityPoolId, key.logins);
                                        }
                                      });

        this.getCredentialsForIdentity = CacheBuilder.newBuilder()
                                      .expireAfterAccess(Duration.ofHours(HOURS_GET_CREDENTIALS_EXPIRATION))
                                      .build(new CacheLoader<GetCredentialsForIdentityIdArgs, Credentials>() {
                                        @Override
                                        public Credentials load(GetCredentialsForIdentityIdArgs key) throws Exception {
                                            return cognitoIdClient.getCredentialsForIdentity(key.identityId, key.logins);
                                        }
                                      });
    }

    @Override
    public List<IdentityPoolShortDescription> getIdentityPools(int pageSize) {
        GetIdentityPoolsArgs args = new GetIdentityPoolsArgs(pageSize);

        try {
            return this.getIdentityPools.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getIdentityId(String identityPoolId, Map<String, String> logins) {
        GetIdentityIdArgs args = new GetIdentityIdArgs(identityPoolId, logins);

        try {
            return this.getIdentityId.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Credentials getCredentialsForIdentity(String identityId, Map<String, String> logins) {
        GetCredentialsForIdentityIdArgs args = new GetCredentialsForIdentityIdArgs(identityId, logins);

        try {
            return this.getCredentialsForIdentity.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class GetIdentityPoolsArgs extends BaseModel {
    int pageSize;

    public GetIdentityPoolsArgs(int pageSize) {
        this.pageSize = pageSize;
    }
}

class GetIdentityIdArgs extends BaseModel {
    String identityPoolId;
    Map<String, String> logins;

    public GetIdentityIdArgs(String identityPoolId, Map<String, String> logins) {
        super();
        this.identityPoolId = identityPoolId;
        this.logins = logins;
    }
}

class GetCredentialsForIdentityIdArgs extends BaseModel {
    String identityId;
    Map<String, String> logins;

    public GetCredentialsForIdentityIdArgs(String identityId, Map<String, String> logins) {
        super();
        this.identityId = identityId;
        this.logins = logins;
    }
}
