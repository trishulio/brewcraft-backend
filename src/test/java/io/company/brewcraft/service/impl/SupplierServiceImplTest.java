package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierAddress;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class SupplierServiceImplTest {
    private SupplierService supplierService;

    private SupplierRepository supplierRepositoryMock;

    @BeforeEach
    public void init() {
        supplierRepositoryMock = mock(SupplierRepository.class);

        supplierService = new SupplierServiceImpl(supplierRepositoryMock);
    }

    @Test
    public void testGetSuppliers_returnsSuppliers() throws Exception {
        Supplier supplier1 = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        List<Supplier> suppliersList = Arrays.asList(supplier1);

        Page<Supplier> expectedSuppliers = new PageImpl<>(suppliersList);

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(supplierRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedSuppliers);

        Page<Supplier> actualSuppliers = supplierService.getSuppliers(0, 100, new TreeSet<>(List.of("id")), true);

        assertSame(0, pageableArgument.getValue().getPageNumber());
        assertSame(100, pageableArgument.getValue().getPageSize());
        assertSame(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertSame("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertSame(1, actualSuppliers.getNumberOfElements());

        Supplier actualSupplier = actualSuppliers.get().findFirst().get();

        assertSame(supplier1.getId(), actualSupplier.getId());
        assertSame(supplier1.getName(), actualSupplier.getName());
        assertSame(supplier1.getAddress().getId(), actualSupplier.getAddress().getId());
        assertSame(supplier1.getContacts().size(), actualSupplier.getContacts().size());
        assertSame(supplier1.getContacts().get(0).getId(), actualSupplier.getContacts().get(0).getId());
        assertSame(supplier1.getLastUpdated(), actualSupplier.getLastUpdated());
        assertSame(supplier1.getCreatedAt(), actualSupplier.getCreatedAt());
        assertSame(supplier1.getVersion(), actualSupplier.getVersion());
    }

    @Test
    public void testGetSupplier_returnsSupplier() throws Exception {
        Long id = 1L;
        Supplier supplier1 = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<Supplier> expectedSupplier = Optional.ofNullable(supplier1);

        when(supplierRepositoryMock.findById(id)).thenReturn(expectedSupplier);

        Supplier actualSupplier = supplierService.getSupplier(id);

        assertEquals(expectedSupplier.get().getId(), actualSupplier.getId());
        assertEquals(expectedSupplier.get().getName(), actualSupplier.getName());
        assertEquals(expectedSupplier.get().getAddress().getId(), actualSupplier.getAddress().getId());
        assertEquals(expectedSupplier.get().getContacts().size(), actualSupplier.getContacts().size());
        assertEquals(expectedSupplier.get().getContacts().get(0).getId(), actualSupplier.getContacts().get(0).getId());
        assertEquals(expectedSupplier.get().getLastUpdated(), actualSupplier.getLastUpdated());
        assertEquals(expectedSupplier.get().getCreatedAt(), actualSupplier.getCreatedAt());
        assertEquals(expectedSupplier.get().getVersion(), actualSupplier.getVersion());
    }

    @Test
    public void testAddSupplier_SavesSupplier() throws Exception {
        Supplier supplierEntity = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Supplier addedSupplier = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        when(supplierRepositoryMock.saveAndFlush(supplierEntity)).thenReturn(addedSupplier);

        Supplier returnedSupplier = supplierService.addSupplier(supplierEntity);

        assertEquals(supplierEntity.getId(), returnedSupplier.getId());
        assertEquals(supplierEntity.getName(), returnedSupplier.getName());
        assertEquals(supplierEntity.getAddress().getId(), returnedSupplier.getAddress().getId());
        assertEquals(supplierEntity.getContacts().size(), returnedSupplier.getContacts().size());
        assertEquals(supplierEntity.getContacts().get(0).getId(), returnedSupplier.getContacts().get(0).getId());
        assertEquals(supplierEntity.getLastUpdated(), returnedSupplier.getLastUpdated());
        assertEquals(supplierEntity.getCreatedAt(), returnedSupplier.getCreatedAt());
        assertEquals(supplierEntity.getVersion(), returnedSupplier.getVersion());
    }

    @Test
    public void testPutSupplier_success() throws Exception {
        Long id = 1L;

        Supplier putSupplier = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        Supplier supplierEntity = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Supplier> persistedSupplierCaptor = ArgumentCaptor.forClass(Supplier.class);

        when(supplierRepositoryMock.saveAndFlush(persistedSupplierCaptor.capture())).thenReturn(supplierEntity);

        Supplier returnedSupplier = supplierService.putSupplier(id, putSupplier);

        //Assert persisted entity
        assertEquals(putSupplier.getId(), persistedSupplierCaptor.getValue().getId());
        assertEquals(putSupplier.getName(), persistedSupplierCaptor.getValue().getName());
        assertEquals(putSupplier.getAddress().getId(), persistedSupplierCaptor.getValue().getAddress().getId());
        assertEquals(putSupplier.getContacts().size(), persistedSupplierCaptor.getValue().getContacts().size());
        assertEquals(putSupplier.getContacts().get(0).getId(), persistedSupplierCaptor.getValue().getContacts().get(0).getId());
        //assertEquals(null, persistedSupplierCaptor.getValue().getLastUpdated());
        //assertEquals(putSupplier.getCreatedAt(), persistedSupplierCaptor.getValue().getCreatedAt());
        assertEquals(putSupplier.getVersion(), persistedSupplierCaptor.getValue().getVersion());

        //Assert returned POJO
        assertEquals(supplierEntity.getId(), returnedSupplier.getId());
        assertEquals(supplierEntity.getName(), returnedSupplier.getName());
        assertEquals(supplierEntity.getAddress().getId(), returnedSupplier.getAddress().getId());
        assertEquals(supplierEntity.getContacts().size(), returnedSupplier.getContacts().size());
        assertEquals(supplierEntity.getContacts().get(0).getId(), returnedSupplier.getContacts().get(0).getId());
        assertEquals(supplierEntity.getLastUpdated(), returnedSupplier.getLastUpdated());
        assertEquals(supplierEntity.getCreatedAt(), returnedSupplier.getCreatedAt());
        assertEquals(supplierEntity.getVersion(), returnedSupplier.getVersion());
    }

    @Test
    public void testPatchSupplier_success() throws Exception {
        Long id = 1L;

        Supplier patchedSupplier = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Supplier existingSupplier = new Supplier(1L, "testName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Supplier persistedSupplier = new Supplier(1L, "updatedName", List.of(new SupplierContact(2L)), new SupplierAddress(1L), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Supplier> persistedSupplierCaptor = ArgumentCaptor.forClass(Supplier.class);

        when(supplierRepositoryMock.findById(id)).thenReturn(Optional.of(existingSupplier));

        when(supplierRepositoryMock.saveAndFlush(persistedSupplierCaptor.capture())).thenReturn(persistedSupplier);

        Supplier returnedSupplier = supplierService.patchSupplier(id, patchedSupplier);

        //Assert persisted entity
        assertEquals(existingSupplier.getId(), persistedSupplierCaptor.getValue().getId());
        assertEquals(patchedSupplier.getName(), persistedSupplierCaptor.getValue().getName());
        assertEquals(patchedSupplier.getAddress().getId(), persistedSupplierCaptor.getValue().getAddress().getId());
        assertEquals(patchedSupplier.getContacts().size(), persistedSupplierCaptor.getValue().getContacts().size());
        assertEquals(patchedSupplier.getContacts().get(0).getId(), persistedSupplierCaptor.getValue().getContacts().get(0).getId());
        assertEquals(existingSupplier.getLastUpdated(), persistedSupplierCaptor.getValue().getLastUpdated());
        assertEquals(existingSupplier.getCreatedAt(), persistedSupplierCaptor.getValue().getCreatedAt());
        assertEquals(existingSupplier.getVersion(), persistedSupplierCaptor.getValue().getVersion());

        //Assert returned POJO
        assertEquals(persistedSupplier.getId(), returnedSupplier.getId());
        assertEquals(persistedSupplier.getName(), returnedSupplier.getName());
        assertEquals(persistedSupplier.getAddress().getId(), returnedSupplier.getAddress().getId());
        assertEquals(persistedSupplier.getContacts().size(), returnedSupplier.getContacts().size());
        assertEquals(persistedSupplier.getContacts().get(0).getId(), returnedSupplier.getContacts().get(0).getId());
        assertEquals(persistedSupplier.getLastUpdated(), returnedSupplier.getLastUpdated());
        assertEquals(persistedSupplier.getCreatedAt(), returnedSupplier.getCreatedAt());
        assertEquals(persistedSupplier.getVersion(), returnedSupplier.getVersion());
    }

    @Test
    public void testPatchSupplier_throwsWhenSupplierDoesNotExist() throws Exception {
        Long id = 1L;
        Supplier updatedSupplierMock = mock(Supplier.class);

        when(supplierRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.patchSupplier(id, updatedSupplierMock);
        });

    }

    @Test
    public void testDeleteSupplier_success() throws Exception {
        Long id = 1L;
        supplierService.deleteSupplier(id);

        verify(supplierRepositoryMock, times(1)).deleteById(id);
    }

    @Test
    public void testSupplierExists() throws Exception {
        Long id = 1L;
        supplierService.supplierExists(id);

        verify(supplierRepositoryMock, times(1)).existsById(id);
    }

    @Test
    public void testSupplierService_classIsTransactional() throws Exception {
        Transactional transactional = supplierService.getClass().getAnnotation(Transactional.class);

        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }

    @Test
    public void testSupplierService_methodsAreNotTransactional() throws Exception {
        Method[] methods = supplierService.getClass().getMethods();
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
