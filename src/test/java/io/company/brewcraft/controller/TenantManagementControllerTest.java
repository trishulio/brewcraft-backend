package io.company.brewcraft.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.service.TenantManagementService;

public class TenantManagementControllerTest {

    private TenantManagementService tenantManagementServiceMock;

    private TenantManagementController tenantManagementController;

    @BeforeEach
    public void init() {
        tenantManagementServiceMock = mock(TenantManagementService.class);
        tenantManagementController = new TenantManagementController(tenantManagementServiceMock);
    }

    @Test
    public void testGetAll() {
        tenantManagementController.getAll();
        verify(tenantManagementServiceMock, times(1)).getTenants();
    }

    @Test
    public void testAddTenant() {
        TenantDto tenantDto = new TenantDto(UUID.randomUUID(), "testName", "testDomain", LocalDateTime.now());
        tenantManagementController.addTenant(tenantDto);
        verify(tenantManagementServiceMock, times(1)).addTenant(tenantDto);
    }

    @Test
    public void testGetTenant() {
        UUID id = UUID.randomUUID();
        tenantManagementController.getTenant(id);
        verify(tenantManagementServiceMock, times(1)).getTenant(id);
    }

    @Test
    public void testDeleteTenant() {
        UUID id = UUID.randomUUID();
        tenantManagementController.deleteTenant(id);
        verify(tenantManagementServiceMock, times(1)).deleteTenant(id);
    }
}
