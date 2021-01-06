package io.company.brewcraft.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.impl.SupplierContactServiceImpl;
import io.company.brewcraft.service.impl.SupplierServiceImpl;
import io.company.brewcraft.service.impl.TenantManagementServiceImpl;

public class ServiceAutoConfigurationTest {

    private ServiceAutoConfiguration serviceAutoConfiguration;

    @BeforeEach
    public void init() {
        serviceAutoConfiguration = new ServiceAutoConfiguration();
    }

    @Test
    public void testTenantManagementService_returnsInstanceOfTenantManagementServiceImpl() {
        TenantManagementService tenantManagementService = serviceAutoConfiguration.tenantManagementService(null, null, null);
        assertTrue(tenantManagementService instanceof TenantManagementServiceImpl);
    }
    
    @Test
    public void testSupplierService_returnsInstanceOfSupplierServiceImpl() {
        SupplierService supplierService = serviceAutoConfiguration.supplierService(null);
        assertTrue(supplierService instanceof SupplierServiceImpl);
    }
    
    @Test
    public void testSupplierContactService_returnsInstanceOfSupplierContactServiceImpl() {
        SupplierContactService supplierContactService = serviceAutoConfiguration.supplierContactService(null, null);
        assertTrue(supplierContactService instanceof SupplierContactServiceImpl);
    }
}
