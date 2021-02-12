package io.company.brewcraft.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierAddressEntityTest {

    private SupplierAddressEntity supplierAddress;

    @BeforeEach
    public void init() {
        supplierAddress = new SupplierAddressEntity();
    }

    @Test
    public void testGetSetId() {
        supplierAddress.setId(1L);
        assertSame(1L, supplierAddress.getId());
    }

    @Test
    public void testGetSetAddressLine1() {
        supplierAddress.setAddressLine1("line1");
        assertSame("line1", supplierAddress.getAddressLine1());
    }
    
    @Test
    public void testGetSetAddressLine2() {
        supplierAddress.setAddressLine2("line2");
        assertSame("line2", supplierAddress.getAddressLine2());
    }

    @Test
    public void testGetSetCity() {
        supplierAddress.setCity("city");
        assertSame("city", supplierAddress.getCity());
    }
    
    @Test
    public void testGetSetCountry() {
        supplierAddress.setCountry("country");
        assertSame("country", supplierAddress.getCountry());
    }
    
    @Test
    public void testGetSetProvince() {
        supplierAddress.setProvince("province");
        assertSame("province", supplierAddress.getProvince());
    }
    
    @Test
    public void testGetPostalCode() {
        supplierAddress.setPostalCode("postalCode");
        assertSame("postalCode", supplierAddress.getPostalCode());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplierAddress.setCreated(created);
        assertSame(created, supplierAddress.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierAddress.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierAddress.getLastUpdated());
    }
}
