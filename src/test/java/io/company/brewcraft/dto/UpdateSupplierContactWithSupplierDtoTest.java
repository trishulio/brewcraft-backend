package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateSupplierContactWithSupplierDtoTest {

    private UpdateSupplierContactWithSupplierDto updateSupplierContactDto;

    @BeforeEach
    public void init() {
        updateSupplierContactDto = new UpdateSupplierContactWithSupplierDto();
    }

    @Test
    public void testConstructor() {
        String firstName = "firstName";
        String lastName = "lastName";
        String position = "position";
        String email = "email";
        String phoneNumber = "phoneNumber";
        Long supplierId = 1L;
        Integer version = 1;

        UpdateSupplierContactWithSupplierDto updateSupplierContactDto = new UpdateSupplierContactWithSupplierDto(firstName, lastName, position, email, phoneNumber, supplierId, version);

        assertSame(firstName, updateSupplierContactDto.getFirstName());
        assertSame(lastName, updateSupplierContactDto.getLastName());
        assertSame(position, updateSupplierContactDto.getPosition());
        assertSame(email, updateSupplierContactDto.getEmail());
        assertSame(phoneNumber, updateSupplierContactDto.getPhoneNumber());
        assertSame(version, updateSupplierContactDto.getVersion());
    }

    @Test
    public void testGetSetFirstName() {
        updateSupplierContactDto.setFirstName("firstNameTest");
        assertSame("firstNameTest", updateSupplierContactDto.getFirstName());
    }

    @Test
    public void testGetSetLastName() {
        updateSupplierContactDto.setLastName("testName");
        assertSame("testName", updateSupplierContactDto.getLastName());
    }

    @Test
    public void testGetSetEmail() {
        updateSupplierContactDto.setEmail("testEmail");
        assertSame("testEmail", updateSupplierContactDto.getEmail());
    }

    @Test
    public void testGetSetPhoneNumber() {
        updateSupplierContactDto.setPhoneNumber("testNumber");
        assertSame("testNumber", updateSupplierContactDto.getPhoneNumber());
    }

    @Test
    public void testGetSetPosition() {
        updateSupplierContactDto.setPosition("position");
        assertSame("position", updateSupplierContactDto.getPosition());
    }

    @Test
    public void testGetSetSupplierId() {
        Long supplierId = 1L;
        updateSupplierContactDto.setSupplierId(supplierId);
        assertSame(supplierId, updateSupplierContactDto.getSupplierId());
    }
}
