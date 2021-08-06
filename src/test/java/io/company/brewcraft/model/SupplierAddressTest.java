package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierAddressTest {

    private SupplierAddress supplierAddress;

    @BeforeEach
    public void init() {
        supplierAddress = new SupplierAddress();
    }
    
    @Test
    public void testAllArgConstructor() {
        supplierAddress = new SupplierAddress(1L, "ADDRESS_1", "ADDRESS_2", "COUNTRY", "PROVINCE", "CITY", "POSTAL_CODE", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));
        
        assertEquals(1L, supplierAddress.getId());
        assertEquals("ADDRESS_1", supplierAddress.getAddressLine1());
        assertEquals("ADDRESS_2", supplierAddress.getAddressLine2());
        assertEquals("COUNTRY", supplierAddress.getCountry());
        assertEquals("PROVINCE", supplierAddress.getProvince());
        assertEquals("CITY", supplierAddress.getCity());
        assertEquals("POSTAL_CODE", supplierAddress.getPostalCode());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), supplierAddress.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), supplierAddress.getLastUpdated());
    }

    @Test
    public void testIdArgConstructor() {
        supplierAddress = new SupplierAddress(1L);

        assertEquals(1L, supplierAddress.getId());
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
        supplierAddress.setCreatedAt(created);
        assertSame(created, supplierAddress.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierAddress.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierAddress.getLastUpdated());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() {
        supplierAddress = new SupplierAddress(1L, "ADDRESS_1", "ADDRESS_2", "COUNTRY", "PROVINCE", "CITY", "POSTAL_CODE", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0));

        final String json = "{\"addressLine1\":\"ADDRESS_1\",\"addressLine2\":\"ADDRESS_2\",\"country\":\"COUNTRY\",\"province\":\"PROVINCE\",\"city\":\"CITY\",\"postalCode\":\"POSTAL_CODE\",\"createdAt\":{\"nano\":0,\"year\":2000,\"monthValue\":1,\"dayOfMonth\":1,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"SATURDAY\",\"dayOfYear\":1,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2001,\"monthValue\":1,\"dayOfMonth\":1,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"MONDAY\",\"dayOfYear\":1,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"id\":1}";
        assertEquals(json, supplierAddress.toString());
    }
}
