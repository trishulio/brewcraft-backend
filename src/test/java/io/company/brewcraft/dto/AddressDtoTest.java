package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressDtoTest {

    private AddressDto addressDto;

    @BeforeEach
    public void init() {
        addressDto = new AddressDto();
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

        AddressDto addressDto = new AddressDto(id, addressLine1, addressLine2, country, province, city, postalCode, created, lastUpdated);
        
        assertSame(id, addressDto.getId());
        assertSame(addressLine1, addressDto.getAddressLine1());
        assertSame(addressLine2, addressDto.getAddressLine2());
        assertSame(country, addressDto.getCountry());
        assertSame(province, addressDto.getProvince());
        assertSame(city, addressDto.getCity());
        assertSame(postalCode, addressDto.getPostalCode());
        assertSame(created, addressDto.getCreated());
        assertSame(lastUpdated, addressDto.getLastUpdated());
    }
    
    @Test
    public void testGetSetId() {
        addressDto.setId(1L);
        assertSame(1L, addressDto.getId());
    }

    @Test
    public void testGetSetAddressLine1() {
        addressDto.setAddressLine1("line1");
        assertSame("line1", addressDto.getAddressLine1());
    }
    
    @Test
    public void testGetSetAddressLine2() {
        addressDto.setAddressLine2("line2");
        assertSame("line2", addressDto.getAddressLine2());
    }

    @Test
    public void testGetSetCity() {
        addressDto.setCity("city");
        assertSame("city", addressDto.getCity());
    }
    
    @Test
    public void testGetSetCountry() {
        addressDto.setCountry("country");
        assertSame("country", addressDto.getCountry());
    }
    
    @Test
    public void testGetSetProvince() {
        addressDto.setProvince("province");
        assertSame("province", addressDto.getProvince());
    }
    
    @Test
    public void testGetPostalCode() {
        addressDto.setPostalCode("postalCode");
        assertSame("postalCode", addressDto.getPostalCode());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        addressDto.setCreated(created);
        assertSame(created, addressDto.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        addressDto.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, addressDto.getLastUpdated());
    }
}
