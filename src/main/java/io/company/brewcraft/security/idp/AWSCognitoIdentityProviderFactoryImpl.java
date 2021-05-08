package io.company.brewcraft.security.idp;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWSCognitoIdentityProviderFactoryImpl implements AWSCognitoIdentityProviderFactory {

    private final AWSCognitoIdentityProvider aWSCognitoIdentityProvider;

    public static final Logger logger = LoggerFactory.getLogger(AWSCognitoIdentityProviderFactoryImpl.class);

    public AWSCognitoIdentityProviderFactoryImpl(final String cognitoRegion, final String cognitoUrl, final String cognitoAccessKey, final String cognitoSecretKey) {
        aWSCognitoIdentityProvider = createAwsCognitoIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey);
    }

    private AWSCognitoIdentityProvider createAwsCognitoIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey) {
        logger.debug("Creating an instance of AWSCognitoIdentityProvider");
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder identityProviderClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        if (StringUtils.isNotEmpty(cognitoAccessKey) && StringUtils.isNotEmpty(cognitoSecretKey)) {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(cognitoAccessKey, cognitoSecretKey);
            identityProviderClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials));
        }
        return identityProviderClientBuilder.build();
    }

    @Override
    public AWSCognitoIdentityProvider getInstance() {
        return aWSCognitoIdentityProvider;
    }
}
