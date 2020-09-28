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
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;

public class AwsSecretsManagerClient implements SecretsManager<String, String> {

    private final AWSSecretsManager client;

    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerClient.class);

    public AwsSecretsManagerClient(@Value("${aws.secretsmanager.region:us-west-1}") String region, @Value("${aws.secretsmanager.url:http://localstack:4566}") String url) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
        client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
    }

    @Override
    public String get(String secretId) throws IOException {
        GetSecretValueRequest req = new GetSecretValueRequest().withSecretId(secretId);
        GetSecretValueResult res = null;

        try {
            res = client.getSecretValue(req);
            String secret = null;
            if (res != null && res.getSecretString() != null) {
                secret = res.getSecretString();
            }

            return secret;

        } catch (ResourceNotFoundException | InvalidRequestException | InvalidParameterException e) {
            logger.error("Error geting secret value for secretId: {}", secretId);
            throw new IOException(e);
        }
    }

    @Override
    public void put(String secretId, String secret) throws IOException {
        CreateSecretRequest req = new CreateSecretRequest().withName(secretId).withSecretString(secret);

        try {
            client.createSecret(req);
        } catch (ResourceNotFoundException | InvalidRequestException | InvalidParameterException e) {
            logger.error("Error createing secret with secretId: {}", secretId);
            throw new IOException(e);
        }
    }
}
