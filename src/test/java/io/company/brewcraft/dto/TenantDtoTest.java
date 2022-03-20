package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

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
    public void testGetSetUrl() throws MalformedURLException {
        URL url = new URL("https://localhost/");
        tenantDto.setUrl(url);
        assertSame(url, tenantDto.getUrl());
    }

    @Test
    public void testGetSetCreatedAt() {
        LocalDateTime created = LocalDateTime.now();
        tenantDto.setCreatedAt(created);
        assertSame(created, tenantDto.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        tenantDto.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, tenantDto.getLastUpdated());
    }
}
