package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.service.SupplierContactService;

public class SupplierContactServiceImplTest {

    private SupplierContactService supplierContactService;
    
    private SupplierContactRepository supplierContactRepositoryMock;
            
    @BeforeEach
    public void init() {
        supplierContactRepositoryMock = mock(SupplierContactRepository.class);
        
        supplierContactService = new SupplierContactServiceImpl(supplierContactRepositoryMock);
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
