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
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class SupplierContactServiceImplTest {

    private SupplierContactService supplierContactService;
    
    private SupplierContactRepository supplierContactRepositoryMock;
    
    private SupplierRepository supplierRepositoryMock;
            
    @BeforeEach
    public void init() {
        supplierContactRepositoryMock = mock(SupplierContactRepository.class);
        supplierRepositoryMock = mock(SupplierRepository.class);
        
        supplierContactService = new SupplierContactServiceImpl(supplierContactRepositoryMock, supplierRepositoryMock);
    }

    @Test
    public void testGetSuppliers_returnsSuppliers() throws Exception {
        SupplierContact supplierContact1 = new SupplierContact();
        SupplierContact supplierContact2 = new SupplierContact();                
        List<SupplierContact> supplierContactList = Arrays.asList(supplierContact1, supplierContact2);
        
        Page<SupplierContact> expectedSupplierContacts = new PageImpl<>(supplierContactList);

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);
        
        when(supplierContactRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedSupplierContacts);

        Page<SupplierContact> actualSupplierContacts = supplierContactService.getSupplierContacts(0, 100, new String[]{"id"}, true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedSupplierContacts.getContent(), actualSupplierContacts.getContent());
    }
    
    @Test
    public void testGetContact_returnsContact() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        Optional<SupplierContact> expectedSupplierContact = Optional.ofNullable(new SupplierContact());
        
        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(true);
        when(supplierContactRepositoryMock.findById(contactId)).thenReturn(expectedSupplierContact);

        SupplierContact actualSupplier = supplierContactService.getContact(supplierId);

        assertSame(expectedSupplierContact.get(), actualSupplier);
    }

    @Test
    public void testAddContact_SavesContact() throws Exception {
        Long supplierId = 1L;
        
        Optional<Supplier> supplier = Optional.ofNullable(new Supplier());
        SupplierContact supplierContactMock = mock(SupplierContact.class);

        when(supplierRepositoryMock.findById(supplierId)).thenReturn(supplier);
          
        supplierContactService.addContact(supplierId, supplierContactMock);
        
        verify(supplierContactMock, times(1)).setSupplier(supplier.get());
        verify(supplierContactRepositoryMock, times(1)).save(supplierContactMock);
    }
    
    @Test
    public void testPutContact_success() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;
        
        Optional<SupplierContact> supplierContact = Optional.ofNullable(new SupplierContact());
        SupplierContact updatedSupplierContactMock = mock(SupplierContact.class);
        
        when(supplierContactRepositoryMock.findById(contactId)).thenReturn(supplierContact);
                
        supplierContactService.putContact(supplierId, contactId, updatedSupplierContactMock);
       
        verify(supplierContactRepositoryMock, times(1)).save(updatedSupplierContactMock);
    }
    
    
    
    @Test
    public void testPatchContact_throwsIfContactDoesNotExist() {
        Long contactId = 1L;
        
        SupplierContact supplierContact = new SupplierContact();

        when(supplierContactRepositoryMock.findById(contactId)).thenReturn(Optional.ofNullable(null));
        
        assertThrows(EntityNotFoundException.class, () -> {
            supplierContactService.patchContact(contactId, supplierContact);
        });
        
    }

    @Test
    public void testDeleteContact_success() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;

        when(supplierRepositoryMock.existsById(supplierId)).thenReturn(true);
        
        supplierContactService.deleteContact(contactId);
        
        verify(supplierContactRepositoryMock, times(1)).deleteById(contactId);
    }
      
    @Test
    public void testSupplierContactService_classIsTransactional() throws Exception {
        Transactional transactional = supplierContactService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testSupplierContactService_methodsAreNotTransactional() throws Exception {
        Method[] methods = supplierContactService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
