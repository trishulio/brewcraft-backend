package io.company.brewcraft.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.impl.TenantRepositoryImpl;

public class RepositoryAutoConfigurationTest {

    private RepositoryAutoConfiguration repositoryAutoConfiguration;

    @BeforeEach
    public void init() {
        repositoryAutoConfiguration = new RepositoryAutoConfiguration();
    }

    @Test
    public void testTenantRepository_returnsInstanceOfTenantRepository() {
        TenantRepository tenantRepository = repositoryAutoConfiguration.tenantRepository(null);
        assertTrue(tenantRepository instanceof TenantRepositoryImpl);
    }
}
