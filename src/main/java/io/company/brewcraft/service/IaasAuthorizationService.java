package io.company.brewcraft.service;

import com.amazonaws.services.cognitoidentity.model.Credentials;

import io.company.brewcraft.model.IaasAuthorization;
import io.company.brewcraft.model.IaasAuthorizationCredentials;

public class IaasAuthorizationService {
    private CognitoIdentityRepository identityRepo;

    
    public IaasAuthorizationService(CognitoIdentityRepository identityRepo) {
        this.identityRepo = identityRepo;
    }

    public IaasAuthorization post(IaasAuthorizationCredentials credentials) {
        Credentials idCreds = identityRepo.getCredentials(credentials.getIdToken().toString());
        IaasAuthorization authorization = IaasAuthorization.from(idCreds.getAccessKeyId(), idCreds.getSecretKey(), idCreds.getSessionToken());
        
        return authorization;
    }
}
