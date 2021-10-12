package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class TenantTest {

    private Tenant tenant;

    @BeforeEach
    public void init() {
        tenant = new Tenant();
    }

    @Test
    public void testAllArgConstructor() {
        tenant = new Tenant(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), "TENANT_1", "TENANT_URL", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));

        assertEquals(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), tenant.getId());
        assertEquals("TENANT_1", tenant.getName());
        assertEquals("TENANT_URL", tenant.getUrl());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), tenant.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), tenant.getLastUpdated());
    }

    @Test
    public void testGetSetId() {
        UUID id = UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9");
        tenant.setId(id);
        assertSame(id, tenant.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        tenant.setName(name);
        assertSame(name, tenant.getName());
    }

    @Test
    public void testGetSetUrl() {
        String url = "testUrl";
        tenant.setUrl(url);
        assertSame(url, tenant.getUrl());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        tenant.setCreatedAt(created);
        assertSame(created, tenant.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        tenant.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, tenant.getLastUpdated());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        tenant = new Tenant(UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9"), "TENANT_1", "TENANT_URL", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));

        final String json = "{\"id\":\"89efec46-fd0b-4fec-bcde-7f4bcef4f8e9\",\"name\":\"TENANT_1\",\"url\":\"TENANT_URL\",\"createdAt\":\"2000-01-01T00:00:00\",\"lastUpdated\":\"2001-01-01T00:00:00\"}";
        JSONAssert.assertEquals(json, tenant.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
