package io.company.brewcraft.security.idp;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

public class AwsCognitoIdentityProviderFactoryImpl implements AwsCognitoIdentityProviderFactory {

    public AWSCognitoIdentityProvider getIdentityProviderClient(final String cognitoRegion, final String cognitoUrl, final String cognitoAccessKey, final String cognitoSecretKey) {
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder identityProviderClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        if (isNotNullAndEmpty(cognitoAccessKey) && isNotNullAndEmpty(cognitoSecretKey)) {
            identityProviderClientBuilder.withCredentials(getAWSCredentialsProvider(cognitoAccessKey, cognitoSecretKey));
        }
        return identityProviderClientBuilder.build();
    }

    private boolean isNotNullAndEmpty(final String str) {
        return str != null && !str.isEmpty();
    }

    private AWSCredentialsProvider getAWSCredentialsProvider(final String cognitoAccessKey, final String cognitoSecretKey) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(cognitoAccessKey, cognitoSecretKey);
        return new AWSStaticCredentialsProvider(basicAWSCredentials);
    }
}
