package io.company.brewcraft.dto;

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
        int version = 1;

        SupplierWithoutContactsDto supplierDto = new SupplierWithoutContactsDto(id, name, address, version);
        assertSame(id, supplierDto.getId());
        assertSame(name, supplierDto.getName());
        assertSame(address, supplierDto.getAddress());
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

}
