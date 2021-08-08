package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddSupplierContactDtoTest {

    private AddSupplierContactDto addSupplierContactDto;

    @BeforeEach
    public void init() {
        addSupplierContactDto = new AddSupplierContactDto();
    }

    @Test
    public void testConstructor() {
        String firstName = "firstName";
        String lastName = "lastName";
        String position = "position";
        String email = "email";
        String phoneNumber = "phoneNumber";
        
        AddSupplierContactDto supplierContactDto = new AddSupplierContactDto(firstName, lastName, position, email, phoneNumber);
        
        assertSame(firstName, supplierContactDto.getFirstName());
        assertSame(lastName, supplierContactDto.getLastName());
        assertSame(position, supplierContactDto.getPosition());
        assertSame(email, supplierContactDto.getEmail());
        assertSame(phoneNumber, supplierContactDto.getPhoneNumber());
    }

    @Test
    public void testGetSetFirstName() {
        addSupplierContactDto.setFirstName("firstNameTest");
        assertSame("firstNameTest", addSupplierContactDto.getFirstName());
    }
    
    @Test
    public void testGetSetLastName() {
        addSupplierContactDto.setLastName("testName");
        assertSame("testName", addSupplierContactDto.getLastName());
    }

    @Test
    public void testGetSetEmail() {
        addSupplierContactDto.setEmail("testEmail");
        assertSame("testEmail", addSupplierContactDto.getEmail());
    }
    
    @Test
    public void testGetSetPhoneNumber() {
        addSupplierContactDto.setPhoneNumber("testNumber");
        assertSame("testNumber", addSupplierContactDto.getPhoneNumber());
    }
    
    @Test
    public void testGetSetPosition() {
        addSupplierContactDto.setPosition("position");
        assertSame("position", addSupplierContactDto.getPosition());
    }
}
