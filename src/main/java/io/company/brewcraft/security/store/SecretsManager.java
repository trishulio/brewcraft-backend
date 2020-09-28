package io.company.brewcraft.security.store;

public interface SecretsManager {

	public String getSecret(String secretId) throws Exception;
	
	public void storeSecret(String secretId, String secret) throws Exception;
	
}
