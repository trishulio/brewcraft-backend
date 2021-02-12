package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SupplierEntityTest {

    private SupplierEntity supplier;

    @BeforeEach
    public void init() {
        supplier = new SupplierEntity();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "Supplier1";
        SupplierAddressEntity address = new SupplierAddressEntity();
        List<SupplierContactEntity> contacts = new ArrayList<>();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        SupplierEntity supplier = new SupplierEntity(id, name, contacts, address, created, lastUpdated, version);
        
        assertSame(id, supplier.getId());
        assertSame(name, supplier.getName());
        assertSame(address, supplier.getAddress());
        assertSame(contacts, supplier.getContacts());
        assertSame(created, supplier.getCreated());
        assertSame(lastUpdated, supplier.getLastUpdated());
        assertSame(version, supplier.getVersion());        
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        supplier.setId(id);
        assertSame(id, supplier.getId());
    }

    @Test
    public void testGetSetName() {
        supplier.setName("testName");
        assertSame("testName", supplier.getName());
    }

    @Test
    public void testGetSetContacts() {
        List<SupplierContactEntity> contacts = new ArrayList<>();
        supplier.setContacts(contacts);
        assertSame(contacts, supplier.getContacts());
    }
    
    @Test
    public void testGetSetAddress() {
        SupplierAddressEntity address = new SupplierAddressEntity();
        supplier.setAddress(address);
        assertSame(address, supplier.getAddress());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        supplier.setVersion(version);
        assertSame(version, supplier.getVersion());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplier.setCreated(created);
        assertSame(created, supplier.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplier.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplier.getLastUpdated());
    }
    
    @Test
    public void testSetContacts_setsSupplierToEachContact() {
        List<SupplierContactEntity> contacts = Arrays.asList(new SupplierContactEntity(), new SupplierContactEntity());
        supplier.setContacts(contacts);
        
        for (SupplierContactEntity contact : supplier.getContacts()) {
            assertSame(supplier, contact.getSupplier());
        }
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSetContacts_clearsAndAddsToExistingListIfExistingListIsNotNull() {
        List<SupplierContactEntity> contactsMock = Mockito.mock(ArrayList.class);
        supplier.setContacts(contactsMock);

        List<SupplierContactEntity> newContactsMock = Mockito.mock(ArrayList.class);
        supplier.setContacts(newContactsMock);
        
        verify(contactsMock, times(1)).clear();
        verify(contactsMock, times(1)).addAll(newContactsMock);
        assertSame(supplier.getContacts(), contactsMock);
    }
}
