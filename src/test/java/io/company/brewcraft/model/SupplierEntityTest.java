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

    private Supplier supplier;

    @BeforeEach
    public void init() {
        supplier = new Supplier();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "Supplier1";
        SupplierAddress address = new SupplierAddress();
        List<SupplierContact> contacts = new ArrayList<>();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Supplier supplier = new Supplier(id, name, contacts, address, created, lastUpdated, version);
        
        assertSame(id, supplier.getId());
        assertSame(name, supplier.getName());
        assertSame(address, supplier.getAddress());
        assertSame(contacts, supplier.getContacts());
        assertSame(created, supplier.getCreatedAt());
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
        List<SupplierContact> contacts = new ArrayList<>();
        supplier.setContacts(contacts);
        assertSame(contacts, supplier.getContacts());
    }
    
    @Test
    public void testGetSetAddress() {
        SupplierAddress address = new SupplierAddress();
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
        supplier.setCreatedAt(created);
        assertSame(created, supplier.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplier.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplier.getLastUpdated());
    }
    
    @Test
    public void testSetContacts_setsSupplierToEachContact() {
        List<SupplierContact> contacts = Arrays.asList(new SupplierContact(), new SupplierContact());
        supplier.setContacts(contacts);
        
        for (SupplierContact contact : supplier.getContacts()) {
            assertSame(supplier, contact.getSupplier());
        }
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testSetContacts_clearsAndAddsToExistingListIfExistingListIsNotNull() {
        List<SupplierContact> contactsMock = Mockito.mock(ArrayList.class);
        supplier.setContacts(contactsMock);

        List<SupplierContact> newContactsMock = Mockito.mock(ArrayList.class);
        supplier.setContacts(newContactsMock);
        
        verify(contactsMock, times(1)).clear();
        verify(contactsMock, times(1)).addAll(newContactsMock);
        assertSame(supplier.getContacts(), contactsMock);
    }
}
