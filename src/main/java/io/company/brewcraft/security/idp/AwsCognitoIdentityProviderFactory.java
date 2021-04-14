package io.company.brewcraft.security.idp;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;

/**
 * Creates instances of {@link AWSCognitoIdentityProvider} for given parameters
 */
public interface AwsCognitoIdentityProviderFactory {

    AWSCognitoIdentityProvider getIdentityProviderClient(String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey);

}
