package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantTest {

    private Tenant tenant;

    @BeforeEach
    public void init() {
        tenant = new Tenant();
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
        tenant.setCreated(created);
        assertSame(created, tenant.getCreated());
    }
}
