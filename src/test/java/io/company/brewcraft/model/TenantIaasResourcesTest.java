package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantIaasResourcesTest {
    private TenantIaasResources resources;

    @BeforeEach
    public void init() {
        resources = new TenantIaasResources(
            new TenantIaasAuthResources(new IaasRole("ROLE")),
            new TenantIaasIdpResources(new IaasIdpTenant("T1")),
            new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY"))
        );
    }

    @Test
    public void testAllArgConstructor() {
        assertEquals(new TenantIaasAuthResources(new IaasRole("ROLE")), resources.getAuthResources());
        assertEquals(new TenantIaasIdpResources(new IaasIdpTenant("T1")), resources.getIdpResources());
        assertEquals(new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY")), resources.getVfsResources());
    }

    @Test
    public void testGetSetAuthResources() {
        resources.setAuthResources(new TenantIaasAuthResources(new IaasRole("ROLE")));
        assertEquals(new TenantIaasAuthResources(new IaasRole("ROLE")), resources.getAuthResources());
    }

    @Test
    public void testGetSetIdpResources() {
        resources.setIdpResources(new TenantIaasIdpResources(new IaasIdpTenant("T1")));
        assertEquals(new TenantIaasIdpResources(new IaasIdpTenant("T1")), resources.getIdpResources());
    }

    @Test
    public void testGetSetVfsResources() {
        resources.setVfsResources(new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY")));
        assertEquals(new TenantIaasVfsResources(new IaasObjectStore("OBJECT_STORE"), new IaasPolicy("POLICY")), resources.getVfsResources());
    }
}
