package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierWithoutContactsDtoTest {

    private SupplierWithoutContactsDto supplierWithoutContactsDto;

    @BeforeEach
    public void init() {
        supplierWithoutContactsDto = new SupplierWithoutContactsDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "Supplier1";
        AddressDto address = new AddressDto();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        SupplierWithoutContactsDto supplierDto = new SupplierWithoutContactsDto(id, name, address, created, lastUpdated, version);
        assertSame(id, supplierDto.getId());
        assertSame(name, supplierDto.getName());
        assertSame(address, supplierDto.getAddress());
        assertSame(created, supplierDto.getCreated());
        assertSame(lastUpdated, supplierDto.getLastUpdated());
        assertSame(version, supplierDto.getVersion());        
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        supplierWithoutContactsDto.setId(id);
        assertSame(id, supplierWithoutContactsDto.getId());
    }

    @Test
    public void testGetSetName() {
        supplierWithoutContactsDto.setName("testName");
        assertSame("testName", supplierWithoutContactsDto.getName());
    }
    
    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        supplierWithoutContactsDto.setAddress(address);
        assertSame(address, supplierWithoutContactsDto.getAddress());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        supplierWithoutContactsDto.setVersion(version);
        assertSame(version, supplierWithoutContactsDto.getVersion());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplierWithoutContactsDto.setCreated(created);
        assertSame(created, supplierWithoutContactsDto.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierWithoutContactsDto.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierWithoutContactsDto.getLastUpdated());
    }
}
