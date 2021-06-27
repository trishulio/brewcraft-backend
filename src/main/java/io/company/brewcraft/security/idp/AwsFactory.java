package io.company.brewcraft.security.idp;


import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;

public interface AwsFactory {
    AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey);

    AWSSecretsManager secretsMgrClient(String region, String url);
}
