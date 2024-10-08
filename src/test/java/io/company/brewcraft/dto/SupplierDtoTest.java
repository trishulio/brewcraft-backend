package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierDtoTest {
    private SupplierDto supplierDto;

    @BeforeEach
    public void init() {
        supplierDto = new SupplierDto();
    }

    @Test
    public void testIdArgConstructor() {
        SupplierDto dto = new SupplierDto(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String name = "Supplier1";
        AddressDto address = new AddressDto();
        List<SupplierContactDto> contacts = new ArrayList<>();
        int version = 1;

        SupplierDto supplierDto = new SupplierDto(id, name, contacts, address, version);
        assertSame(id, supplierDto.getId());
        assertSame(name, supplierDto.getName());
        assertSame(address, supplierDto.getAddress());
        assertSame(contacts, supplierDto.getContacts());
        assertSame(version, supplierDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        supplierDto.setId(id);
        assertSame(id, supplierDto.getId());
    }

    @Test
    public void testGetSetName() {
        supplierDto.setName("testName");
        assertSame("testName", supplierDto.getName());
    }

    @Test
    public void testGetSetContacts() {
        List<SupplierContactDto> contacts = new ArrayList<>();
        supplierDto.setContacts(contacts);
        assertSame(contacts, supplierDto.getContacts());
    }

    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        supplierDto.setAddress(address);
        assertSame(address, supplierDto.getAddress());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        supplierDto.setVersion(version);
        assertSame(version, supplierDto.getVersion());
    }
}
