package io.company.brewcraft.security.store;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.PutSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.UpdateSecretRequest;

public class AwsSecretsManagerClient implements SecretsManager<String, String> {

    private final AWSSecretsManager client;

    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerClient.class);

    public AwsSecretsManagerClient(@Value("${aws.secretsmanager.region}") String region, @Value("${aws.secretsmanager.url}") String url) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
        client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
    }

    @Override
    public String get(String secretId) throws IOException {
        GetSecretValueRequest getSecretRequest = new GetSecretValueRequest().withSecretId(secretId);
        GetSecretValueResult result = null;

        try {
            String secret = null;
            result = client.getSecretValue(getSecretRequest);
            if (result != null && result.getSecretString() != null) {
                secret = result.getSecretString();
            }
            
            return secret;
            
        } catch (ResourceNotFoundException | InvalidRequestException | InvalidParameterException e) {
            logger.error("Error geting secret value for secretId: {}", secretId);
            throw new IOException(e);
        }
    }

    @Override
    public void put(String secretId, String secret) throws IOException {
        PutSecretValueRequest updateSecretRequest = new PutSecretValueRequest().withSecretId(secretId).withSecretString(secret);

        try {
            client.putSecretValue(updateSecretRequest);
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error updating secret with secretId: {}", secretId);
            throw new IOException(e);
        }
    }
    
    public void create(String secretId, String secret) throws IOException {    
        CreateSecretRequest createSecretRequest = new CreateSecretRequest().withName(secretId).withSecretString(secret);

        try {
            client.createSecret(createSecretRequest);
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error createing secret with secretId: {}", secretId);
            throw new IOException(e);
        }
    }
    
    public void update(String secretId, String secret) throws IOException { 
        UpdateSecretRequest updateSecretRequest = new UpdateSecretRequest().withSecretId(secretId).withSecretString(secret);

        try {
            client.updateSecret(updateSecretRequest);
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error updating secret with secretId: {}", secretId);
            throw new IOException(e);
        }
    }
}
