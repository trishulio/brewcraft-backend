package io.company.brewcraft.security.idp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
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

    public AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKeyId, String cognitoAccessSecretKey) {
        logger.debug("Creating an instance of AwsCognitoIdp");
        final AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(cognitoUrl, cognitoRegion);
        final AWSCognitoIdentityProviderClientBuilder idpClientBuilder = AWSCognitoIdentityProviderClient.builder().withEndpointConfiguration(endpointConfig);
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(cognitoAccessKeyId, cognitoAccessSecretKey);
        idpClientBuilder.withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials));

        AWSCognitoIdentityProvider awsCognitoIdp = idpClientBuilder.build();

        return awsCognitoIdp;
    }

    public AWSSecretsManager secretsMgrClient(String region, String url, String accessKeyId, String accessSecretKey) {
        AWSCredentials creds = new BasicAWSCredentials(accessKeyId, accessSecretKey);
        AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);

        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                                                                 .withEndpointConfiguration(endpointConfig)
                                                                 .withCredentials(credsProvider)
                                                                 .build();

        return client;
    }

    public AmazonCognitoIdentity getAwsCognitoIdentityClient(String region, String accessKeyId, String accessSecretKey) {
        AWSCredentials creds = new BasicAWSCredentials(accessKeyId, accessSecretKey);
        AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);
        AmazonCognitoIdentity cognitoIdentityClient = AmazonCognitoIdentityClientBuilder.standard()
                                                                                        .withCredentials(credsProvider)
                                                                                        .withRegion(region)
                                                                                        .build();
        return cognitoIdentityClient;
    }

    public AmazonS3 s3Client(String region, String s3AccessKeyId, String s3Secret) {
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(s3AccessKeyId, s3Secret);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(basicAwsCredentials);

        return AmazonS3ClientBuilder.standard()
                                     .withRegion(region)
                                     .withCredentials(credsProvider)
                                     .build();
    }

    public AmazonS3 s3Client(String region, String s3AccessKeyId, String s3Secret, String sessionToken) {
        AWSCredentials awsCreds = new BasicSessionCredentials(s3AccessKeyId, s3Secret, sessionToken);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(awsCreds);

        return AmazonS3ClientBuilder.standard()
                                     .withRegion(region)
                                     .withCredentials(credsProvider)
                                     .build();
    }

    public AmazonIdentityManagement iamClient(String iamAccessKeyId, String iamSecret) {
        BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(iamAccessKeyId, iamSecret);
        AWSCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(basicAwsCredentials);

        AmazonIdentityManagement awsIamClient = AmazonIdentityManagementClientBuilder.standard()
                                                .withCredentials(credsProvider)
                                                // Note: IAM services are non-regional. The region defaults to US_EAST_1.
                                                // We hard-code a value here to avoid making network calls that builder
                                                // makes behind the scenes when this parameter is not being set.
                                                .withRegion(Regions.US_EAST_1)
                                                .build();

        return awsIamClient;
    }
}
