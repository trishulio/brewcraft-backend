package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.migration.MigrationManager;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.service.TenantIaasService;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.TenantMapper;

public class TenantManagementServiceImplTest {

    private TenantManagementService tenantManagementService;

    private TenantRepository tenantRepositoryMock;

    private MigrationManager migrationManagerMock;

    private TenantMapper tenantMapperMock;

    private TenantIaasService iaasServiceMock;

    @BeforeEach
    public void init() {
        tenantRepositoryMock = mock(TenantRepository.class);
        migrationManagerMock = mock(MigrationManager.class);
        tenantMapperMock = mock(TenantMapper.class);
        iaasServiceMock = mock(TenantIaasService.class);
        tenantManagementService = new TenantManagementServiceImpl(tenantRepositoryMock, migrationManagerMock, iaasServiceMock, tenantMapperMock);
    }

    @Test
    public void testGetTenants_returnsTenants() throws Exception {
        Tenant tenant1 = new Tenant(UUID.randomUUID(), "testName", "testUrl", LocalDateTime.now(), LocalDateTime.now());
        Tenant tenant2 = new Tenant(UUID.randomUUID(), "testName2", "testUrl", LocalDateTime.now(), LocalDateTime.now());

        TenantDto tenantDto1 = new TenantDto(UUID.randomUUID(), "testName", "testUrl", LocalDateTime.now(), LocalDateTime.now());
        TenantDto tenantDto2 = new TenantDto(UUID.randomUUID(), "testName2", "testUrl2", LocalDateTime.now(), LocalDateTime.now());

        List<Tenant> tenants = Arrays.asList(tenant1, tenant2);

        when(tenantRepositoryMock.findAll()).thenReturn(tenants);
        when(tenantMapperMock.tenantToTenantDto(tenant1)).thenReturn(tenantDto1);
        when(tenantMapperMock.tenantToTenantDto(tenant2)).thenReturn(tenantDto2);

        List<TenantDto> actualTenants = tenantManagementService.getTenants();
        List<TenantDto> expectedTenants = Arrays.asList(tenantDto1, tenantDto2);

        assertEquals(actualTenants, expectedTenants);
    }

    @Test
    public void testGetTenant_returnsTenant() throws Exception {
        UUID id = UUID.randomUUID();
        Optional<Tenant> tenant = Optional.ofNullable(new Tenant(id, "testName", "testUrl", LocalDateTime.now(), LocalDateTime.now()));
        TenantDto expectedTenantDto = new TenantDto(id, "testName", "testUrl", LocalDateTime.now(), LocalDateTime.now());

        when(tenantRepositoryMock.findById(id)).thenReturn(tenant);
        when(tenantMapperMock.tenantToTenantDto(tenant.get())).thenReturn(expectedTenantDto);

        TenantDto actualTenantDto = tenantManagementService.getTenant(id);

        assertSame(expectedTenantDto, actualTenantDto);
    }

    @Test
    public void testGetTenant_throwsEntityNotFoundException() throws Exception {
        UUID id = UUID.randomUUID();

        when(tenantRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            tenantManagementService.getTenant(id);
            verify(tenantMapperMock, times(0)).tenantToTenantDto(Mockito.any(Tenant.class));
        });
    }

    @Test
    public void testAddTenant_returnsId() throws Exception {
        TenantDto tenantDto = new TenantDto(null, "testName", "testUrl", null, null);
        Tenant tenant = new Tenant(null, "testName", "testUrl", null, null);

        UUID expectedId = UUID.fromString("924343cb-6a3d-4513-8459-db1fc5a9c1e5");
        Tenant tenantWithId = new Tenant(expectedId, "testName", "testUrl", null, null);

        when(tenantMapperMock.tenantDtoToTenant(tenantDto)).thenReturn(tenant);
        when(tenantRepositoryMock.saveAndFlush(tenant)).thenReturn(tenantWithId);

        UUID actualId = tenantManagementService.addTenant(tenantDto);

        verify(migrationManagerMock, times(1)).migrate("924343cb_6a3d_4513_8459_db1fc5a9c1e5");
        assertSame(expectedId, actualId);
    }

    @Test
    public void testAddTenant_throwsDuplicateKeyException() throws Exception {
        Tenant tenant = new Tenant();

        when(tenantRepositoryMock.save(tenant)).thenThrow(new DuplicateKeyException("Tenant"));

        assertThrows(DuplicateKeyException.class, () -> {
            tenantRepositoryMock.save(tenant);
        });
    }

    @Test
    public void testAddTenant_deletesTenantAndThrowsWhenMigrationFails() throws Exception {
        TenantDto tenantDto = new TenantDto(null, "testName", "testUrl", null, null);
        Tenant tenant = new Tenant(null, "testName", "testUrl", null, null);

        UUID expectedId = UUID.fromString("924343cb-6a3d-4513-8459-db1fc5a9c1e5");
        Tenant tenantWithId = new Tenant(expectedId, "testName", "testUrl", null, null);

        when(tenantMapperMock.tenantDtoToTenant(tenantDto)).thenReturn(tenant);
        when(tenantRepositoryMock.saveAndFlush(tenant)).thenReturn(tenantWithId);
        doThrow(new EntityNotFoundException(null, null)).when(migrationManagerMock).migrate("924343cb_6a3d_4513_8459_db1fc5a9c1e5");

        assertThrows(EntityNotFoundException.class, () -> {
            tenantManagementService.addTenant(tenantDto);
            verify(migrationManagerMock, times(1)).migrate("924343cb_6a3d_4513_8459_db1fc5a9c1e5");
            verify(tenantRepositoryMock, times(1)).deleteById(expectedId);
        });
    }

    @Test
    public void testUpdateTenant_success() throws Exception {
        TenantDto tenantDto = new TenantDto(null, "testNameNew", "testUrlNew", null, null);
        UUID id = UUID.fromString("924343cb-6a3d-4513-8459-db1fc5a9c1e5");
        Optional<Tenant> tenant = Optional.ofNullable(new Tenant(id, "testName", "testUrl", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 5, 6, 7, 8)));

        when(tenantRepositoryMock.findById(id)).thenReturn(tenant);

        tenantManagementService.updateTenant(tenantDto, id);

        ArgumentCaptor<Tenant> tenantArgument = ArgumentCaptor.forClass(Tenant.class);
        verify(tenantRepositoryMock, times(1)).save(tenantArgument.capture());

        assertEquals(id, tenantArgument.getValue().getId());
        assertEquals("testNameNew", tenantArgument.getValue().getName());
        assertEquals("testUrlNew", tenantArgument.getValue().getUrl());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), tenantArgument.getValue().getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 5, 6, 7, 8), tenantArgument.getValue().getLastUpdated());
    }

    @Test
    public void testUpdateTenant_throwsEntityNotFoundException() throws Exception {
        UUID id = UUID.fromString("924343cb-6a3d-4513-8459-db1fc5a9c1e5");
        TenantDto tenantDto = new TenantDto(id, "testName", "testUrl", null, null);

        when(tenantRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            tenantManagementService.updateTenant(tenantDto, id);
            verify(tenantRepositoryMock, times(0)).save(Mockito.any(Tenant.class));
        });
    }

    @Test
    public void testDeleteTenant_success() throws Exception {
        UUID id = UUID.randomUUID();
        tenantManagementService.deleteTenant(id);

        verify(tenantRepositoryMock, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteTenant_throwsEntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("Tenant", "1")).when(tenantRepositoryMock).deleteById(Mockito.any(UUID.class));

        assertThrows(EntityNotFoundException.class, () -> {
            tenantManagementService.deleteTenant(UUID.randomUUID());
        });
    }

    @Test
    public void testTenantManagementService_classIsTransactional() throws Exception {
        Transactional transactional = tenantManagementService.getClass().getAnnotation(Transactional.class);

        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }

    @Test
    public void testTenantManagementService_methodsAreNotTransactional() throws Exception {
        Method[] methods = tenantManagementService.getClass().getMethods();
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
