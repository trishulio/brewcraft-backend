package io.company.brewcraft;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.configuration.AwsConfiguration;
import io.company.brewcraft.security.store.AwsSecretsManagerClient;
import io.company.brewcraft.security.store.SecretsManager;

public class AwsConfigurationTest {
    private AwsConfiguration config;

    @BeforeEach
    public void init() {
        config = new AwsConfiguration();
    }

    @Test
    public void testSecretsManager_ReturnsInstanceOfAwsSecretsManager() {
        SecretsManager<String, String> mgr = config.secretManager(null);
        assertTrue(mgr instanceof AwsSecretsManagerClient);
    }
}
