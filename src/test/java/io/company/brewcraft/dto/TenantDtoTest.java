package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TenantDtoTest {

    private TenantDto tenantDto;

    @BeforeEach
    public void init() {
        tenantDto = new TenantDto();
    }

    @Test
    public void testGetSetId() {
        UUID id = UUID.fromString("89efec46-fd0b-4fec-bcde-7f4bcef4f8e9");
        tenantDto.setId(id);
        assertSame(id, tenantDto.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        tenantDto.setName(name);
        assertSame(name, tenantDto.getName());
    }

    @Test
    public void testGetSetUrl() {
        String url = "testUrl";
        tenantDto.setUrl(url);
        assertSame(url, tenantDto.getUrl());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        tenantDto.setCreated(created);
        assertSame(created, tenantDto.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        tenantDto.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, tenantDto.getLastUpdated());
    }
}
