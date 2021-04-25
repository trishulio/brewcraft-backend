package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class SupplierContactServiceImplTest {

    private SupplierContactService supplierContactService;
    
    private SupplierContactRepository supplierContactRepositoryMock;
    
    private SupplierService supplierServiceMock;
            
    @BeforeEach
    public void init() {
        supplierContactRepositoryMock = mock(SupplierContactRepository.class);
        supplierServiceMock = mock(SupplierService.class);
        
        supplierContactService = new SupplierContactServiceImpl(supplierContactRepositoryMock, supplierServiceMock);
    }

    @Test
    public void testGetContacts_returnsContacts() throws Exception {
        SupplierContact contactEntity = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        List<SupplierContact> contactEntities = Arrays.asList(contactEntity);
        
        Page<SupplierContact> expectedContactEntity = new PageImpl<>(contactEntities);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(supplierContactRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedContactEntity);

        Page<SupplierContact> actualContacts = supplierContactService.getSupplierContacts(0, 100, Set.of("id"), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(1, actualContacts.getNumberOfElements());
        
        SupplierContact actualContact = actualContacts.get().findFirst().get();

        assertEquals(contactEntity.getId(), actualContact.getId());
        assertEquals(contactEntity.getFirstName(), actualContact.getFirstName());
        assertEquals(contactEntity.getLastName(), actualContact.getLastName());
        assertEquals(contactEntity.getPhoneNumber(), actualContact.getPhoneNumber());
        assertEquals(contactEntity.getPosition(), actualContact.getPosition());
        assertEquals(contactEntity.getEmail(), actualContact.getEmail());
        assertEquals(contactEntity.getSupplier().getId(), actualContact.getSupplier().getId());
        assertEquals(contactEntity.getLastUpdated(), actualContact.getLastUpdated());
        assertEquals(contactEntity.getCreatedAt(), actualContact.getCreatedAt());
        assertEquals(contactEntity.getVersion(), actualContact.getVersion());      
    }
    
    @Test
    public void testGetContact_returnsContact() throws Exception {
        Long id = 1L;
        SupplierContact contactEntity = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<SupplierContact> expectedContactEntity = Optional.ofNullable(contactEntity);

        when(supplierContactRepositoryMock.findById(id)).thenReturn(expectedContactEntity);

        SupplierContact returnedContact = supplierContactService.getContact(id);

        assertEquals(expectedContactEntity.get().getId(), returnedContact.getId());
        assertEquals(expectedContactEntity.get().getFirstName(), returnedContact.getFirstName());
        assertEquals(expectedContactEntity.get().getLastName(), returnedContact.getLastName());
        assertEquals(expectedContactEntity.get().getPhoneNumber(), returnedContact.getPhoneNumber());
        assertEquals(expectedContactEntity.get().getPosition(), returnedContact.getPosition());
        assertEquals(expectedContactEntity.get().getEmail(), returnedContact.getEmail());
        assertEquals(expectedContactEntity.get().getSupplier().getId(), returnedContact.getSupplier().getId());
        assertEquals(expectedContactEntity.get().getLastUpdated(), returnedContact.getLastUpdated());
        assertEquals(expectedContactEntity.get().getCreatedAt(), returnedContact.getCreatedAt());
        assertEquals(expectedContactEntity.get().getVersion(), returnedContact.getVersion());  
    }

    @Test
    public void testAddContact_Success() throws Exception {
        Long supplierId = 2L;
        
        SupplierContact supplierContact = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        when(supplierServiceMock.getSupplier(supplierId)).thenReturn(new Supplier(2L));
        
        when(supplierContactRepositoryMock.saveAndFlush(supplierContact)).thenReturn(supplierContact);

        SupplierContact returnedContact = supplierContactService.addContact(supplierId, supplierContact);
            
        assertEquals(supplierContact.getId(), returnedContact.getId());
        assertEquals(supplierContact.getFirstName(), returnedContact.getFirstName());
        assertEquals(supplierContact.getLastName(), returnedContact.getLastName());
        assertEquals(supplierContact.getPhoneNumber(), returnedContact.getPhoneNumber());
        assertEquals(supplierContact.getPosition(), returnedContact.getPosition());
        assertEquals(supplierContact.getEmail(), returnedContact.getEmail());
        assertEquals(supplierContact.getSupplier().getId(), returnedContact.getSupplier().getId());
        assertEquals(supplierContact.getLastUpdated(), returnedContact.getLastUpdated());
        assertEquals(supplierContact.getCreatedAt(), returnedContact.getCreatedAt());
        assertEquals(supplierContact.getVersion(), returnedContact.getVersion());  
    }
    
    @Test
    public void testAddContact_throwsWhenSupplierDoesNotExist() throws Exception {
        Long supplierId = 2L;
        
        SupplierContact supplierContact = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(supplierServiceMock.getSupplier(supplierId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            supplierContactService.addContact(supplierId, supplierContact);
            verify(supplierContactRepositoryMock, times(0)).saveAndFlush(Mockito.any(SupplierContact.class));
        });
    }
    
    @Test
    public void testPutContact_success() throws Exception {
        Long supplierId = 2L;
        Long id = 1L;

        SupplierContact putContact = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        SupplierContact supplierEntity = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<SupplierContact> persistedContactCaptor = ArgumentCaptor.forClass(SupplierContact.class);

        when(supplierServiceMock.getSupplier(supplierId)).thenReturn(new Supplier(2L));

        when(supplierContactRepositoryMock.saveAndFlush(persistedContactCaptor.capture())).thenReturn(supplierEntity);

        SupplierContact returnedContact = supplierContactService.putContact(supplierId, id, putContact);
       
        //Assert persisted entity
        assertEquals(putContact.getId(), persistedContactCaptor.getValue().getId());
        assertEquals(putContact.getFirstName(), persistedContactCaptor.getValue().getFirstName());
        assertEquals(putContact.getLastName(), persistedContactCaptor.getValue().getLastName());
        assertEquals(putContact.getPhoneNumber(), persistedContactCaptor.getValue().getPhoneNumber());
        assertEquals(putContact.getPosition(), persistedContactCaptor.getValue().getPosition());
        assertEquals(putContact.getEmail(), persistedContactCaptor.getValue().getEmail());
        assertEquals(putContact.getSupplier().getId(), persistedContactCaptor.getValue().getSupplier().getId());
        //assertEquals(null, persistedContactCaptor.getValue().getLastUpdated());
        //assertEquals(putContact.getCreatedAt(), persistedContactCaptor.getValue().getCreatedAt());
        assertEquals(putContact.getVersion(), persistedContactCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(supplierEntity.getId(), returnedContact.getId());
        assertEquals(supplierEntity.getFirstName(), returnedContact.getFirstName());
        assertEquals(supplierEntity.getLastName(), returnedContact.getLastName());
        assertEquals(supplierEntity.getPhoneNumber(), returnedContact.getPhoneNumber());
        assertEquals(supplierEntity.getPosition(), returnedContact.getPosition());
        assertEquals(supplierEntity.getEmail(), returnedContact.getEmail());
        assertEquals(supplierEntity.getSupplier().getId(), returnedContact.getSupplier().getId());
        assertEquals(supplierEntity.getLastUpdated(), returnedContact.getLastUpdated());
        assertEquals(supplierEntity.getCreatedAt(), returnedContact.getCreatedAt());
        assertEquals(supplierEntity.getVersion(), returnedContact.getVersion());  
    }
     
    @Test
    public void testPutContact_throwsIfSupplierDoesNotExist() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;

        SupplierContact putContact = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(supplierContactRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            supplierContactService.putContact(parentCategoryId, id, putContact);
            verify(supplierContactRepositoryMock, times(0)).saveAndFlush(Mockito.any(SupplierContact.class));
        });
    }
    
    @Test
    public void testPatchContact_success() throws Exception {
        Long supplierId = 2L;
        Long id = 1L;
        SupplierContact patchedContact = new SupplierContact(1L, null, "updatedName", null, null, null, null, null, null, null);
        SupplierContact existingContactEntity = new SupplierContact(1L, new Supplier(2L), "name1", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        SupplierContact persistedContactEntity = new SupplierContact(1L, new Supplier(2L), "updatedName", "lastName1", "position1", "email1", "phoneNumber1", LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<SupplierContact> persistedContactCaptor = ArgumentCaptor.forClass(SupplierContact.class);

        when(supplierContactRepositoryMock.findById(id)).thenReturn(Optional.of(existingContactEntity));

        when(supplierServiceMock.getSupplier(supplierId)).thenReturn(new Supplier(2L));
        
        when(supplierContactRepositoryMock.saveAndFlush(persistedContactCaptor.capture())).thenReturn(persistedContactEntity);

        SupplierContact returnedContact = supplierContactService.patchContact(id, supplierId, patchedContact);
       
        //Assert persisted entity
        assertEquals(existingContactEntity.getId(), persistedContactCaptor.getValue().getId());
        assertEquals(patchedContact.getFirstName(), persistedContactCaptor.getValue().getFirstName());
        assertEquals(existingContactEntity.getLastName(), persistedContactCaptor.getValue().getLastName());
        assertEquals(existingContactEntity.getPhoneNumber(), persistedContactCaptor.getValue().getPhoneNumber());
        assertEquals(existingContactEntity.getPosition(), persistedContactCaptor.getValue().getPosition());
        assertEquals(existingContactEntity.getEmail(), persistedContactCaptor.getValue().getEmail());
        assertEquals(existingContactEntity.getSupplier().getId(), persistedContactCaptor.getValue().getSupplier().getId());
        assertEquals(existingContactEntity.getLastUpdated(), persistedContactCaptor.getValue().getLastUpdated());
        //assertEquals(existingMaterialCategoryEntity.getCreatedAt(), persistedContactCaptor.getValue().getCreatedAt());
        assertEquals(existingContactEntity.getVersion(), persistedContactCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedContactEntity.getId(), returnedContact.getId());
        assertEquals(persistedContactEntity.getFirstName(), returnedContact.getFirstName());
        assertEquals(persistedContactEntity.getLastName(), returnedContact.getLastName());
        assertEquals(persistedContactEntity.getPhoneNumber(), returnedContact.getPhoneNumber());
        assertEquals(persistedContactEntity.getPosition(), returnedContact.getPosition());
        assertEquals(persistedContactEntity.getEmail(), returnedContact.getEmail());
        assertEquals(persistedContactEntity.getSupplier().getId(), returnedContact.getSupplier().getId());
        assertEquals(persistedContactEntity.getLastUpdated(), returnedContact.getLastUpdated());
        assertEquals(persistedContactEntity.getCreatedAt(), returnedContact.getCreatedAt());
        assertEquals(persistedContactEntity.getVersion(), returnedContact.getVersion());  
    }
    
   
    @Test
    public void testPatchContact_throwsIfSupplierDoesNotExist() throws Exception {
        Long id = 1L;
        Long supplierId = 2L;
        SupplierContact contact = new SupplierContact();
        
        when(supplierContactRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(null));
      
        assertThrows(EntityNotFoundException.class, () -> {
            supplierContactService.patchContact(id, supplierId, contact);
            verify(supplierContactRepositoryMock, times(0)).saveAndFlush(Mockito.any(SupplierContact.class));
        });
    }

    @Test
    public void testDeleteContact_success() throws Exception {
        Long supplierId = 1L;
        Long contactId = 1L;

        when(supplierServiceMock.supplierExists(supplierId)).thenReturn(true);
        
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
