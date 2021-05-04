package io.company.brewcraft.security.idp;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AWSCognitoIdentityProviderFactoryImplTest {

    private AWSCognitoIdentityProviderFactoryImpl awsCognitoIdentityProviderFactoryImpl;

    @Test
    public void testGetInstance_ReturnsAWSCognitoIdentityProviderWithBasicCredentials_WhenSecretAndAccessKeyProvided() throws IllegalAccessException {
        final String cognitoRegion = "testCognitoRegion";
        final String cognitoUrl = "testCognitoUrl";
        final String cognitoAccessKey = "testCognitoAccessKey";
        final String cognitoSecretKey = "testCognitoSecretKey";
        final AWSCognitoIdentityProviderFactoryImpl awsCognitoIdentityProviderFactoryImpl = new AWSCognitoIdentityProviderFactoryImpl(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoSecretKey);

        final AWSCognitoIdentityProvider awsCognitoIdentityProvider = awsCognitoIdentityProviderFactoryImpl.getInstance();

        final AWSCredentialsProvider awsCredentialsProvider = (AWSCredentialsProvider) FieldUtils.readField(awsCognitoIdentityProvider, "awsCredentialsProvider", true);
        assertTrue(awsCredentialsProvider instanceof AWSStaticCredentialsProvider);
        assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(), cognitoAccessKey);
        assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(), cognitoSecretKey);
    }

    @Test
    public void testGetInstance_ReturnsAWSCognitoIdentityProviderWithDefaultCredentials_WhenSecretAndAccessKeyNotProvided() throws IllegalAccessException {
        final String cognitoRegion = "testCognitoRegion";
        final String cognitoUrl = "testCognitoUrl";
        final AWSCognitoIdentityProviderFactoryImpl awsCognitoIdentityProviderFactoryImpl = new AWSCognitoIdentityProviderFactoryImpl(cognitoRegion, cognitoUrl, null, null);

        final AWSCognitoIdentityProviderClient awsCognitoIdentityProvider = (AWSCognitoIdentityProviderClient) awsCognitoIdentityProviderFactoryImpl.getInstance();

        final AWSCredentialsProvider awsCredentialsProvider = (AWSCredentialsProvider) FieldUtils.readField(awsCognitoIdentityProvider, "awsCredentialsProvider", true);
        assertTrue(awsCredentialsProvider instanceof DefaultAWSCredentialsProviderChain);
    }
}
