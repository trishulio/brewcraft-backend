package io.company.brewcraft.security.idp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;

public class AwsFactory {
    private static final Logger logger = LoggerFactory.getLogger(AwsFactory.class);

    public AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey) {
        logger.debug("Creating an instance of AwsCognitoIdp");
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder idpClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(cognitoAccessKey, cognitoSecretKey);
        idpClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials));

        AWSCognitoIdentityProvider awsCognitoIdp = idpClientBuilder.build();

        return awsCognitoIdp;
    }

    public AWSSecretsManager secretsMgrClient(String region, String url) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();

        return client;
    }

    public AmazonCognitoIdentity getAwsCognitoIdentityClient(String region, String accessKey, String accessSecret) {
        AWSCredentials creds = new BasicAWSCredentials(accessKey, accessSecret);
        AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);
        AmazonCognitoIdentity cognitoIdentityClient = AmazonCognitoIdentityClientBuilder.standard()
                                                                                        .withCredentials(credsProvider)
                                                                                        .withRegion(region)
                                                                                        .build();
        return cognitoIdentityClient;
    }

    public AmazonS3 s3Client(String region, String s3AccessKey, String s3Secret) {
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(s3AccessKey, s3Secret);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(basicAwsCredentials);
        
        return AmazonS3ClientBuilder.standard()
                                     .withRegion(region)
                                     .withCredentials(credsProvider)
                                     .build();
    }
    
    public AmazonS3 s3Client(String region, String s3AccessKey, String s3Secret, String sessionToken) {
        AWSCredentials awsCreds = new BasicSessionCredentials(s3AccessKey, s3Secret, sessionToken);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(awsCreds);
        
        return AmazonS3ClientBuilder.standard()
                                     .withRegion(region)
                                     .withCredentials(credsProvider)
                                     .build();
    }
    
    public AmazonIdentityManagement iamClient(final String region, String iamAccessKey, String iamSecret) {
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(iamAccessKey, iamSecret);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(basicAwsCredentials);

        AmazonIdentityManagement awsIamClient = AmazonIdentityManagementClientBuilder.standard()
                                                .withRegion(region)
                                                .withCredentials(credsProvider)
                                                .build();
        
        return awsIamClient;
    }
}
