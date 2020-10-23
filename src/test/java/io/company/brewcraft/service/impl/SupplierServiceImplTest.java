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
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierMapper;

public class SupplierServiceImplTest {

    private SupplierService supplierService;

    private SupplierRepository supplierRepositoryMock;
        
    private SupplierMapper supplierMapperMock;

    @BeforeEach
    public void init() {
        supplierRepositoryMock = mock(SupplierRepository.class);
        supplierMapperMock = mock(SupplierMapper.class);
        supplierService = new SupplierServiceImpl(supplierRepositoryMock, supplierMapperMock);
    }

    @Test
    public void testGetSuppliers_returnsSuppliers() throws Exception {
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();

        SupplierDto supplierDto1 = new SupplierDto();
        SupplierDto supplierDto2 = new SupplierDto();
                
        List<Supplier> suppliers = Arrays.asList(supplier1, supplier2);

        when(supplierRepositoryMock.findAll()).thenReturn(suppliers);
        when(supplierMapperMock.supplierToSupplierDto(supplier1)).thenReturn(supplierDto1);
        when(supplierMapperMock.supplierToSupplierDto(supplier2)).thenReturn(supplierDto2);

        List<SupplierDto> actualSuppliers = supplierService.getSuppliers();
        List<SupplierDto> expectedSuppliers = Arrays.asList(supplierDto1, supplierDto2);

        assertEquals(expectedSuppliers, actualSuppliers);
    }
    
    @Test
    public void testGetSupplier_returnsSupplier() throws Exception {
        Long id = 1L;
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());
        SupplierDto expectedSupplierDto = new SupplierDto();

        when(supplierRepositoryMock.findById(id)).thenReturn(supplier);
        when(supplierMapperMock.supplierToSupplierDto(supplier.get())).thenReturn(expectedSupplierDto);

        SupplierDto actualSupplierDto = supplierService.getSupplier(id);

        assertSame(expectedSupplierDto, actualSupplierDto);
    }

    @Test
    public void testGetSupplier_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        
        when(supplierRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.getSupplier(id);
            verify(supplierMapperMock, times(0)).supplierToSupplierDto(Mockito.any(Supplier.class));
        });
    }

    @Test
    public void testAddSupplier_SavesSupplier() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        Supplier supplier = new Supplier();
        
        when(supplierMapperMock.supplierDtoToSupplier(supplierDto)).thenReturn(supplier);

        supplierService.addSupplier(supplierDto);
        
        verify(supplierRepositoryMock, times(1)).save(supplier);
    }
    
    @Test
    public void testUpdateTenant_success() throws Exception {
        Long id = 1L;
        SupplierDto supplierDto = new SupplierDto();
        Supplier supplier = new Supplier(id, "Supplier 1", Arrays.asList(new SupplierContact(), new SupplierContact()), null, null, null, null);
        
        when(supplierRepositoryMock.existsById(id)).thenReturn(true);
        when(supplierMapperMock.supplierDtoToSupplier(supplierDto)).thenReturn(supplier);
                
        supplierService.updateSupplier(supplierDto, id);
       
        ArgumentCaptor<Supplier> supplierArgument = ArgumentCaptor.forClass(Supplier.class);
        verify(supplierRepositoryMock, times(1)).save(supplierArgument.capture());
        assertSame(id, supplierArgument.getValue().getId());
    }
    
    @Test
    public void testUpdateTenant_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        SupplierDto supplierDto = new SupplierDto();
        
        when(supplierRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.updateSupplier(supplierDto, id);
            verify(supplierRepositoryMock, times(0)).save(Mockito.any(Supplier.class));
        });
    }

    @Test
    public void testDeleteTenant_success() throws Exception {
        Long id = 1L;
        supplierService.deleteSupplier(id);
        
        verify(supplierRepositoryMock, times(1)).deleteById(id);
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
