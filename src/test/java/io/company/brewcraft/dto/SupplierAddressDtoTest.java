package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierAddressDtoTest {

    private SupplierAddressDto supplierAddressDto;

    @BeforeEach
    public void init() {
        supplierAddressDto = new SupplierAddressDto();
    }

    @Test
    public void testConstructor() {      
        Long id = 1L;
        String addressLine1 = "addressLine1";
        String addressLine2 = "addressLine2";
        String country = "country";
        String province = "province";
        String city = "city";
        String postalCode = "postalCode";
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);

        SupplierAddressDto supplierAddressDto = new SupplierAddressDto(id, addressLine1, addressLine2, country, province, city, postalCode, created, lastUpdated);
        
        assertSame(id, supplierAddressDto.getId());
        assertSame(addressLine1, supplierAddressDto.getAddressLine1());
        assertSame(addressLine2, supplierAddressDto.getAddressLine2());
        assertSame(country, supplierAddressDto.getCountry());
        assertSame(province, supplierAddressDto.getProvince());
        assertSame(city, supplierAddressDto.getCity());
        assertSame(postalCode, supplierAddressDto.getPostalCode());
        assertSame(created, supplierAddressDto.getCreated());
        assertSame(lastUpdated, supplierAddressDto.getLastUpdated());
    }
    
    @Test
    public void testGetSetId() {
        supplierAddressDto.setId(1L);
        assertSame(1L, supplierAddressDto.getId());
    }

    @Test
    public void testGetSetAddressLine1() {
        supplierAddressDto.setAddressLine1("line1");
        assertSame("line1", supplierAddressDto.getAddressLine1());
    }
    
    @Test
    public void testGetSetAddressLine2() {
        supplierAddressDto.setAddressLine2("line2");
        assertSame("line2", supplierAddressDto.getAddressLine2());
    }

    @Test
    public void testGetSetCity() {
        supplierAddressDto.setCity("city");
        assertSame("city", supplierAddressDto.getCity());
    }
    
    @Test
    public void testGetSetCountry() {
        supplierAddressDto.setCountry("country");
        assertSame("country", supplierAddressDto.getCountry());
    }
    
    @Test
    public void testGetSetProvince() {
        supplierAddressDto.setProvince("province");
        assertSame("province", supplierAddressDto.getProvince());
    }
    
    @Test
    public void testGetPostalCode() {
        supplierAddressDto.setPostalCode("postalCode");
        assertSame("postalCode", supplierAddressDto.getPostalCode());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplierAddressDto.setCreated(created);
        assertSame(created, supplierAddressDto.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierAddressDto.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierAddressDto.getLastUpdated());
    }
}
