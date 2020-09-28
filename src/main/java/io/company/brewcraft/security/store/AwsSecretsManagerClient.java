package io.company.brewcraft.security.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.CreateSecretResult;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

@Component
public class AwsSecretsManagerClient implements SecretsManager {
	
	private final AWSSecretsManager client;
		
	private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerClient.class);
	
	public AwsSecretsManagerClient(@Value("${aws.secretsmanager.region:us-west-1}") String region, 
			@Value("${aws.secretsmanager.url:http://localstack:4566}") String url) {
		AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(url, region);
		client = AWSSecretsManagerClientBuilder.standard().withEndpointConfiguration(endpointConfig).build();
	}

	public String getSecret(String secretId) throws Exception {		
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretId);
		GetSecretValueResult getSecretValueResult = null;
		
		try {
			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		} catch (Exception e) {
			logger.error("Error geting secret value for secretId: {}", secretId);
			throw e;
		}
		
		String secret = "";
		if (getSecretValueResult != null && getSecretValueResult.getSecretString() != null) {
			secret = getSecretValueResult.getSecretString();
		} 

		return secret;
	}
	
	
	public void storeSecret(String secretId, String secret) throws Exception {
		CreateSecretRequest createSecretRequest = new CreateSecretRequest().withName(secretId).withSecretString(secret);
	    
		try {
			CreateSecretResult createSecretResult = client.createSecret(createSecretRequest);
		} catch (Exception e) {
			logger.error("Error createing secret with secretId: {}", secretId);
			throw e;
		}
	}

}
