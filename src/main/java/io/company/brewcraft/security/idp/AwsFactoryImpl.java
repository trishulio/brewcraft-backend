package io.company.brewcraft.security.idp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;

public class AwsFactoryImpl implements AwsFactory {
    private static final Logger logger = LoggerFactory.getLogger(AwsFactoryImpl.class);

    @Override
    public AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey) {
        logger.debug("Creating an instance of AwsCognitoIdp");
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder idpClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(cognitoAccessKey, cognitoSecretKey);
        idpClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials));

        AWSCognitoIdentityProvider awsCognitoIdp = idpClientBuilder.build();

        return awsCognitoIdp;
    }

    @Override
    public AWSSecretsManager secretsMgrClient(String region, String url) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();

        return client;
    }
}
