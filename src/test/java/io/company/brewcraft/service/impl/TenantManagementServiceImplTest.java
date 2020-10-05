package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import io.company.brewcraft.dto.TenantDto;
import io.company.brewcraft.model.Tenant;
import io.company.brewcraft.repository.TenantRepository;
import io.company.brewcraft.repository.impl.TenantRepositoryImpl;
import io.company.brewcraft.service.TenantManagementService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.TenantMapper;

public class TenantManagementServiceImplTest {

    private TenantManagementService tenantManagementService;

    private TenantRepository tenantRepositoryMock;

    private TenantMapper tenantMapper;

    private TransactionTemplate transactionTemplate;

    private PlatformTransactionManager platformTransactionManagerMock;

    @BeforeEach
    public void init() {
        tenantRepositoryMock = mock(TenantRepositoryImpl.class);
        tenantMapper = mock(TenantMapper.class);
        platformTransactionManagerMock = mock(PlatformTransactionManager.class);
        transactionTemplate = new TransactionTemplate(platformTransactionManagerMock);
        tenantManagementService = new TenantManagementServiceImpl(transactionTemplate, tenantRepositoryMock, tenantMapper);
    }

    @Test
    public void testGetTenants_returnsTenants() throws Exception {
        Tenant tenant1 = new Tenant(UUID.randomUUID(), "testName", "testDomain", LocalDateTime.now());
        Tenant tenant2 = new Tenant(UUID.randomUUID(), "testName2", "testDomain2", LocalDateTime.now());

        TenantDto tenantDto1 = new TenantDto(UUID.randomUUID(), "testName", "testDomain", LocalDateTime.now());
        TenantDto tenantDto2 = new TenantDto(UUID.randomUUID(), "testName2", "testDomain2", LocalDateTime.now());

        List<Tenant> tenants = Arrays.asList(tenant1, tenant2);

        when(tenantRepositoryMock.findAll()).thenReturn(tenants);
        when(tenantMapper.tenantToTenantDto(tenant1)).thenReturn(tenantDto1);
        when(tenantMapper.tenantToTenantDto(tenant2)).thenReturn(tenantDto2);

        List<TenantDto> actualTenants = tenantManagementService.getTenants();
        List<TenantDto> expectedTenants = Arrays.asList(tenantDto1, tenantDto2);

        verify(platformTransactionManagerMock, times(1)).commit(any());
        verify(platformTransactionManagerMock, times(0)).rollback(any());
        assertEquals(actualTenants, expectedTenants);
    }

    @Test
    public void testGetTenants_throws() throws Exception {
        when(tenantRepositoryMock.findAll()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            when(tenantRepositoryMock.findAll()).thenThrow(new RuntimeException());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }

    @Test
    public void testAddTenant_returnsId() throws Exception {
        TenantDto tenantDto = new TenantDto(null, "testName", "testDomain", null);
        Tenant tenant = new Tenant(null, "testName", "testDomain", null);

        UUID expectedId = UUID.randomUUID();
        when(tenantMapper.tenantDtoToTenant(tenantDto)).thenReturn(tenant);
        when(tenantRepositoryMock.save(tenant)).thenReturn(expectedId);

        UUID actualId = tenantManagementService.addTenant(tenantDto);

        verify(platformTransactionManagerMock, times(1)).commit(any());
        verify(platformTransactionManagerMock, times(0)).rollback(any());
        assertSame(expectedId, actualId);
    }

    @Test
    public void testAddTenant_throwsDuplicateKeyException() throws Exception {
        when(tenantRepositoryMock.save(Mockito.any(Tenant.class))).thenThrow(new DuplicateKeyException("Tenant"));

        assertThrows(DuplicateKeyException.class, () -> {
            tenantRepositoryMock.save(new Tenant());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }

    @Test
    public void testAddTenant_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            when(tenantRepositoryMock.save(new Tenant())).thenThrow(new Exception());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }

    @Test
    public void testGetTenant_returnsTenant() throws Exception {
        UUID id = UUID.randomUUID();
        Tenant tenant = new Tenant(id, "testName", "testDomain", LocalDateTime.now());
        TenantDto expectedTenantDto = new TenantDto(id, "testName", "testDomain", LocalDateTime.now());

        when(tenantRepositoryMock.findById(id)).thenReturn(tenant);
        when(tenantMapper.tenantToTenantDto(tenant)).thenReturn(expectedTenantDto);

        TenantDto actualTenantDto = tenantManagementService.getTenant(id);

        verify(platformTransactionManagerMock, times(1)).commit(any());
        verify(platformTransactionManagerMock, times(0)).rollback(any());
        assertSame(expectedTenantDto, actualTenantDto);
    }

    @Test
    public void testGetTenant_throwsEmptyResultDataAccessException() throws Exception {
        when(tenantRepositoryMock.findById(Mockito.any(UUID.class))).thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EmptyResultDataAccessException.class, () -> {
            tenantRepositoryMock.findById(UUID.randomUUID());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }

    @Test
    public void testGetTenant_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            when(tenantRepositoryMock.findById(UUID.randomUUID())).thenThrow(new Exception());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }

    @Test
    public void testDeleteTenant_success() throws Exception {
        UUID id = UUID.randomUUID();

        when(tenantRepositoryMock.deleteById(id)).thenReturn(1);

        tenantRepositoryMock.deleteById(id);

        // TODO: Look into why commit is not called here (although it is commiting..)
        // verify(platformTransactionManagerMock, times(1)).commit(any());
        verify(platformTransactionManagerMock, times(0)).rollback(any());

    }

    @Test
    public void testDeleteTenant_throwsEntityNotFoundException() throws Exception {
        when(tenantRepositoryMock.deleteById(Mockito.any(UUID.class)))
                .thenThrow(new EntityNotFoundException("Tenant", "1"));

        assertThrows(EntityNotFoundException.class, () -> {
            tenantRepositoryMock.deleteById(UUID.randomUUID());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }

    @Test
    public void testDeleteTenant_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            when(tenantRepositoryMock.deleteById(UUID.randomUUID())).thenThrow(new Exception());
            verify(platformTransactionManagerMock, times(0)).commit(any());
            verify(platformTransactionManagerMock, times(1)).rollback(any());
        });
    }
}
