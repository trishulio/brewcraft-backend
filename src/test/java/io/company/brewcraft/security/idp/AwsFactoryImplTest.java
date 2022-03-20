package io.company.brewcraft.security.idp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;

public class AwsFactoryImplTest {

    private AwsFactory factory;

    @BeforeEach
    public void init() {
        factory = new AwsFactory();
    }

    @Test
    public void testGetInstance_ReturnsAWSCognitoIdentityProviderWithBasicCredentials_WhenSecretAndAccessKeyProvided() throws IllegalAccessException {
        final String cognitoRegion = "testCognitoRegion";
        final String cognitoUrl = "testCognitoUrl";
        final String cognitoAccessKey = "testCognitoAccessKey";
        final String cognitoAccessSecret = "testCognitoAccessSecret";

        final AWSCognitoIdentityProvider awsCognitoIdp = factory.getIdentityProvider(cognitoRegion, cognitoUrl, cognitoAccessKey, cognitoAccessSecret);

        final AWSCredentialsProvider awsCredentialsProvider = (AWSCredentialsProvider) FieldUtils.readField(awsCognitoIdp, "awsCredentialsProvider", true);
        assertTrue(awsCredentialsProvider instanceof AWSStaticCredentialsProvider);
        assertEquals(awsCredentialsProvider.getCredentials().getAWSAccessKeyId(), cognitoAccessKey);
        assertEquals(awsCredentialsProvider.getCredentials().getAWSSecretKey(), cognitoAccessSecret);
    }
}
