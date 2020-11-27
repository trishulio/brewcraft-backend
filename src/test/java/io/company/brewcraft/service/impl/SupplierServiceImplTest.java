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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.EntityHelper;

public class SupplierServiceImplTest {

    private SupplierService supplierService;

    private SupplierRepository supplierRepositoryMock;
    
    private SupplierContactRepository supplierContactRepositoryMock;
    
    private EntityHelper entityHelperMock;
        
    @BeforeEach
    public void init() {
        supplierRepositoryMock = mock(SupplierRepository.class);
        supplierContactRepositoryMock = mock(SupplierContactRepository.class);
        entityHelperMock = mock(EntityHelper.class);
        
        supplierService = new SupplierServiceImpl(supplierRepositoryMock, supplierContactRepositoryMock, entityHelperMock);
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
    public void testGetSupplier_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        
        when(supplierRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.getSupplier(id);
        });
    }

    @Test
    public void testAddSupplier_SavesSupplier() throws Exception {
        Supplier supplier = new Supplier();
        
        supplierService.addSupplier(supplier);
        
        verify(supplierRepositoryMock, times(1)).save(supplier);
    }
    
    @Test
    public void testUpdateSupplier_success() throws Exception {
        Long id = 1L;
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());
        Supplier updatedSupplierMock = mock(Supplier.class);
        
        when(supplierRepositoryMock.findById(id)).thenReturn(supplier);
                
        supplierService.updateSupplier(id, updatedSupplierMock);
       
        verify(entityHelperMock, times(1)).applyUpdate(updatedSupplierMock, supplier.get());
        verify(supplierRepositoryMock, times(1)).save(updatedSupplierMock);
    }
    
    @Test
    public void testUpdateSupplier_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Supplier supplier = new Supplier();
        
        when(supplierRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));
      
        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.updateSupplier(id, supplier);
            verify(supplierRepositoryMock, times(0)).save(Mockito.any(Supplier.class));
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
    public void testGetContacts_returnsContacts() throws Exception {
        Long supplierId = 1L;
                      
        List<SupplierContact> expectedSupplierContacts = Arrays.asList();
       
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(true);
        when(supplierContactRepositoryMock.findAllBySupplierId(supplierId)).thenReturn(expectedSupplierContacts);

        List<SupplierContact> actualSupplierContacts = supplierService.getContacts(supplierId);

        assertEquals(expectedSupplierContacts, actualSupplierContacts);
    }
    
    @Test
    public void testGetContacts_throwsEntityNotFoundException() throws Exception {
        Long supplierId = 1L;
 
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.getContacts(supplierId);
        });
    }
    
    @Test
    public void testGetContact_returnsContact() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        Optional<SupplierContact> expectedSupplierContact = Optional.ofNullable(new SupplierContact());
        
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(true);
        when(supplierContactRepositoryMock.findById(contactId)).thenReturn(expectedSupplierContact);

        SupplierContact actualSupplier = supplierService.getContact(supplierId, contactId);

        assertSame(expectedSupplierContact.get(), actualSupplier);
    }

    @Test
    public void testGetContact_throwsEntityNotFoundException() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.getContact(supplierId, contactId);
        });
    }

    @Test
    public void testAddContact_SavesContact() throws Exception {
        Long supplierId = 1L;
        
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());
        SupplierContact supplierContactMock = mock(SupplierContact.class);

        when(supplierRepositoryMock.findById(supplierId)).thenReturn(supplier);
          
        supplierService.addContact(supplierId, supplierContactMock);
        
        verify(supplierContactMock, times(1)).setSupplier(supplier.get());
        verify(supplierContactRepositoryMock, times(1)).save(supplierContactMock);
    }
    
    @Test
    public void testAddContact_throwsEntityNotFoundException() throws Exception {
        Long supplierId = 1L;
        
        SupplierContact supplierContact = new SupplierContact();

        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.addContact(supplierId, supplierContact);
        });
    }
    
    @Test
    public void testUpdateContact_success() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        Optional<SupplierContact> supplierContact = Optional.ofNullable(new SupplierContact());
        SupplierContact updatedSupplierContactMock = mock(SupplierContact.class);
        
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(true);
        when(supplierContactRepositoryMock.findById(contactId)).thenReturn(supplierContact);
                
        supplierService.updateContact(supplierId, contactId, updatedSupplierContactMock);
       
        verify(entityHelperMock, times(1)).applyUpdate(updatedSupplierContactMock, supplierContact.get());
        verify(supplierContactRepositoryMock, times(1)).save(updatedSupplierContactMock);
    }
    
    @Test
    public void testUpdateContact_throwsEntityNotFoundException() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        SupplierContact updatedSupplierContactMock = mock(SupplierContact.class);
        
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.updateContact(supplierId, contactId, updatedSupplierContactMock);
            verify(supplierContactRepositoryMock, times(0)).save(Mockito.any(SupplierContact.class));
        });
    }

    @Test
    public void testDeleteContact_success() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;

        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(true);
        
        supplierService.deleteContact(supplierId, contactId);
        
        verify(supplierContactRepositoryMock, times(1)).deleteById(contactId);
    }
    
    @Test
    public void testDeleteContact_throwsEntityNotFoundException() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            supplierService.deleteContact(supplierId, contactId);
        });
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
