package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();                
        List<Supplier> suppliersList = Arrays.asList(supplier1, supplier2);
        
        Page<Supplier> expectedSuppliers = new PageImpl<>(suppliersList);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(supplierRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedSuppliers);

        Page<Supplier> actualSuppliers = supplierService.getSuppliers(0, 100, new String[]{"id"}, true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedSuppliers.getContent(), actualSuppliers.getContent());
    }
    
    @Test
    public void testGetSupplier_returnsSupplier() throws Exception {
        Long id = 1L;
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());

        when(supplierRepositoryMock.findById(id)).thenReturn(supplier);

        Supplier actualSupplier = supplierService.getSupplier(id);

        assertSame(supplier.get(), actualSupplier);
    }

    @Test
    public void testAddSupplier_SavesSupplier() throws Exception {
        Supplier supplier = new Supplier();
        
        supplierService.addSupplier(supplier);
        
        verify(supplierRepositoryMock, times(1)).save(supplier);
    }
    
    @Test
    public void testPutSupplier_success() throws Exception {
        Long id = 1L;
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());
        Supplier updatedSupplierMock = mock(Supplier.class);
        
        when(supplierRepositoryMock.findById(id)).thenReturn(supplier);
                
        supplierService.putSupplier(id, updatedSupplierMock);
       
        verify(supplierRepositoryMock, times(1)).save(updatedSupplierMock);
    }
    
    @Test
    public void testPatchSupplier_success() throws Exception {
        Long id = 1L;
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());
        Supplier updatedSupplierMock = mock(Supplier.class);
        
        when(supplierRepositoryMock.findById(id)).thenReturn(supplier);
                
        supplierService.patchSupplier(id, updatedSupplierMock);
       
        verify(supplierRepositoryMock, times(1)).save(updatedSupplierMock);
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
