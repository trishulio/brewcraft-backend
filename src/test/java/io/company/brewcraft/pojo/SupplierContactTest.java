package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierContactTest {

    private SupplierContact supplierContact;

    @BeforeEach
    public void init() {
        supplierContact = new SupplierContact();
    }

    @Test
    public void testGetSetId() {
        supplierContact.setId(1L);
        assertSame(1L, supplierContact.getId());
    }

    @Test
    public void testGetSetFirstName() {
        supplierContact.setFirstName("firstNameTest");
        assertSame("firstNameTest", supplierContact.getFirstName());
    }
    
    @Test
    public void testGetSetLastName() {
        supplierContact.setLastName("testName");
        assertSame("testName", supplierContact.getLastName());
    }

    @Test
    public void testGetSetEmail() {
        supplierContact.setEmail("testEmail");
        assertSame("testEmail", supplierContact.getEmail());
    }
    
    @Test
    public void testGetSetPhoneNumber() {
        supplierContact.setPhoneNumber("testNumber");
        assertSame("testNumber", supplierContact.getPhoneNumber());
    }
    
    @Test
    public void testGetSetPosition() {
        supplierContact.setPosition("position");
        assertSame("position", supplierContact.getPosition());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplierContact.setCreatedAt(created);
        assertSame(created, supplierContact.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierContact.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierContact.getLastUpdated());
    }
}
