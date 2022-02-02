package io.company.brewcraft.service;

import java.util.Map;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;

public class CognitoIdentityClient {
    private final AmazonCognitoIdentity awsCognitoIdentityClient;
    private String identityPoolId;
    private String userPoolId;

    public CognitoIdentityClient(AmazonCognitoIdentity awsCognitoIdentityClient, String identityPoolId, String userPoolId) {
        this.awsCognitoIdentityClient = awsCognitoIdentityClient;
        this.identityPoolId = identityPoolId;
        this.userPoolId = userPoolId;
    }
    
    public String getId(String idToken) {
        GetIdRequest request = new GetIdRequest()
                                    .withIdentityPoolId(identityPoolId)
                                    .withLogins(Map.of(userPoolId, idToken));

        GetIdResult result = awsCognitoIdentityClient.getId(request);
        
        return result.getIdentityId();
    }
    
    public Credentials getCredentialsForIdentity(String identityId, String idToken) {
        GetCredentialsForIdentityRequest request = new GetCredentialsForIdentityRequest()
                                                        .withIdentityId(identityId)
                                                        .withLogins(Map.of(userPoolId, idToken));

        GetCredentialsForIdentityResult result = awsCognitoIdentityClient.getCredentialsForIdentity(request);
        
        return result.getCredentials();
    }
}
