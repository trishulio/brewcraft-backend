package io.company.brewcraft.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;

public class ServiceAutoConfigurationTest {

    private ServiceAutoConfiguration serviceAutoConfiguration;

    @BeforeEach
    public void init() {
        serviceAutoConfiguration = new ServiceAutoConfiguration();
    }

    @Test
    public void testTenantRepository_returnsInstanceOfTenantRepository() {
        TenantManagementService tenantManagementService = serviceAutoConfiguration.tenantManagementService(null, null);
        assertTrue(tenantManagementService instanceof TenantManagementServiceImpl);
    }
}
