package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierContactDtoTest {
    private SupplierContactDto supplierContactDto;

    @BeforeEach
    public void init() {
        supplierContactDto = new SupplierContactDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String firstName = "firstName";
        String lastName = "lastName";
        String position = "position";
        String email = "email";
        String phoneNumber = "phoneNumber";
        Integer version = 1;

        SupplierContactDto supplierContactDto = new SupplierContactDto(id, firstName, lastName, position, email, phoneNumber, version);

        assertSame(id, supplierContactDto.getId());
        assertSame(firstName, supplierContactDto.getFirstName());
        assertSame(lastName, supplierContactDto.getLastName());
        assertSame(position, supplierContactDto.getPosition());
        assertSame(email, supplierContactDto.getEmail());
        assertSame(phoneNumber, supplierContactDto.getPhoneNumber());
        assertSame(version, supplierContactDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        supplierContactDto.setId(1L);
        assertSame(1L, supplierContactDto.getId());
    }

    @Test
    public void testGetSetFirstName() {
        supplierContactDto.setFirstName("firstNameTest");
        assertSame("firstNameTest", supplierContactDto.getFirstName());
    }

    @Test
    public void testGetSetLastName() {
        supplierContactDto.setLastName("testName");
        assertSame("testName", supplierContactDto.getLastName());
    }

    @Test
    public void testGetSetEmail() {
        supplierContactDto.setEmail("testEmail");
        assertSame("testEmail", supplierContactDto.getEmail());
    }

    @Test
    public void testGetSetPhoneNumber() {
        supplierContactDto.setPhoneNumber("testNumber");
        assertSame("testNumber", supplierContactDto.getPhoneNumber());
    }

    @Test
    public void testGetSetPosition() {
        supplierContactDto.setPosition("position");
        assertSame("position", supplierContactDto.getPosition());
    }
}
