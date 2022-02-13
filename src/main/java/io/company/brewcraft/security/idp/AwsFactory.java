package io.company.brewcraft.security.idp;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;

public interface AwsFactory {
    AWSCognitoIdentityProvider getIdentityProvider(final String cognitoRegion, String cognitoUrl, String cognitoAccessKey, String cognitoSecretKey);

    AWSSecretsManager secretsMgrClient(String region, String url);
    
    AmazonCognitoIdentity getAwsCognitoIdentityClient(final String region);
    
    AmazonS3 s3Client(String region, String s3AccessKey, String s3Secret);
    
    AmazonS3 s3Client(String region, String s3AccessKey, String s3Secret, String sessionToken);
    
    AmazonIdentityManagement iamClient(final String region, String iamAccessKey, String iamSecret);
}
