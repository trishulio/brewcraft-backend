package io.company.brewcraft.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddSupplierDtoTest {

    private AddSupplierDto addSupplierDto;

    @BeforeEach
    public void init() {
        addSupplierDto = new AddSupplierDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "Supplier1";
        AddressDto address = new AddressDto();
        List<SupplierContactDto> contacts = new ArrayList<>();

        AddSupplierDto supplierDto = new AddSupplierDto(name, contacts, address);
        assertSame(name, supplierDto.getName());
        assertSame(address, supplierDto.getAddress());
        assertSame(contacts, supplierDto.getContacts());
    }

    @Test
    public void testGetSetName() {
        addSupplierDto.setName("testName");
        assertSame("testName", addSupplierDto.getName());
    }

    @Test
    public void testGetSetContacts() {
        List<SupplierContactDto> contacts = new ArrayList<>();
        addSupplierDto.setContacts(contacts);
        assertSame(contacts, addSupplierDto.getContacts());
    }
    
    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        addSupplierDto.setAddress(address);
        assertSame(address, addSupplierDto.getAddress());
    }

}
