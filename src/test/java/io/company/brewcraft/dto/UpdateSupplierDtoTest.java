package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateSupplierDtoTest {

    private UpdateSupplierDto updateSupplierDto;

    @BeforeEach
    public void init() {
        updateSupplierDto = new UpdateSupplierDto();
    }

    @Test
    public void testConstructor() {
        String name = "Supplier1";
        AddressDto address = new AddressDto();
        List<SupplierContactDto> contacts = new ArrayList<>();
        int version = 1;

        UpdateSupplierDto updateSupplierDto = new UpdateSupplierDto(name, contacts, address, version);
        assertSame(name, updateSupplierDto.getName());
        assertSame(address, updateSupplierDto.getAddress());
        assertSame(contacts, updateSupplierDto.getContacts());
        assertSame(version, updateSupplierDto.getVersion());
    }

    @Test
    public void testGetSetName() {
        updateSupplierDto.setName("testName");
        assertSame("testName", updateSupplierDto.getName());
    }

    @Test
    public void testGetSetContacts() {
        List<SupplierContactDto> contacts = new ArrayList<>();
        updateSupplierDto.setContacts(contacts);
        assertSame(contacts, updateSupplierDto.getContacts());
    }

    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        updateSupplierDto.setAddress(address);
        assertSame(address, updateSupplierDto.getAddress());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateSupplierDto.setVersion(version);
        assertSame(version, updateSupplierDto.getVersion());
    }

}
