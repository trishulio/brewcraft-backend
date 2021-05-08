package io.company.brewcraft.security.idp;


import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;

public interface AWSCognitoIdentityProviderFactory {
    AWSCognitoIdentityProvider getInstance();
}
