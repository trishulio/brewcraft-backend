package io.company.brewcraft.model;

public class IaasAuthorizationCredentials {

    private CognitoIdToken idToken;
    
    public CognitoIdToken getIdToken() {
        return this.idToken;
    }
    
    public void setIdToken(CognitoIdToken idToken) {
        this.idToken = idToken;
    }
}
