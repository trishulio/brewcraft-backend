
package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierContactWithSupplierDtoTest {
    
    private SupplierContactWithSupplierDto supplierContactWithSupplierDto;

    @BeforeEach
    public void init() {
        supplierContactWithSupplierDto = new SupplierContactWithSupplierDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String firstName = "firstName";
        String lastName = "lastName";
        String position = "position";
        String email = "email";
        String phoneNumber = "phoneNumber";
        SupplierWithoutContactsDto supplier = new SupplierWithoutContactsDto();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;
        
        SupplierContactWithSupplierDto supplierContactWithSupplierDto = new SupplierContactWithSupplierDto(id, firstName, lastName, position, email, phoneNumber, supplier, created, lastUpdated, version);
        
        assertSame(id, supplierContactWithSupplierDto.getId());
        assertSame(firstName, supplierContactWithSupplierDto.getFirstName());
        assertSame(lastName, supplierContactWithSupplierDto.getLastName());
        assertSame(position, supplierContactWithSupplierDto.getPosition());
        assertSame(email, supplierContactWithSupplierDto.getEmail());
        assertSame(phoneNumber, supplierContactWithSupplierDto.getPhoneNumber());
        assertSame(supplier, supplierContactWithSupplierDto.getSupplier());
        assertSame(created, supplierContactWithSupplierDto.getCreated());
        assertSame(lastUpdated, supplierContactWithSupplierDto.getLastUpdated());
        assertSame(version, supplierContactWithSupplierDto.getVersion());
    }
    
    @Test
    public void testGetSetId() {
        supplierContactWithSupplierDto.setId(1L);
        assertSame(1L, supplierContactWithSupplierDto.getId());
    }

    @Test
    public void testGetSetFirstName() {
        supplierContactWithSupplierDto.setFirstName("firstNameTest");
        assertSame("firstNameTest", supplierContactWithSupplierDto.getFirstName());
    }
    
    @Test
    public void testGetSetLastName() {
        supplierContactWithSupplierDto.setLastName("testName");
        assertSame("testName", supplierContactWithSupplierDto.getLastName());
    }

    @Test
    public void testGetSetEmail() {
        supplierContactWithSupplierDto.setEmail("testEmail");
        assertSame("testEmail", supplierContactWithSupplierDto.getEmail());
    }
    
    @Test
    public void testGetSetPhoneNumber() {
        supplierContactWithSupplierDto.setPhoneNumber("testNumber");
        assertSame("testNumber", supplierContactWithSupplierDto.getPhoneNumber());
    }
    
    @Test
    public void testGetSetPosition() {
        supplierContactWithSupplierDto.setPosition("position");
        assertSame("position", supplierContactWithSupplierDto.getPosition());
    }
    
    @Test
    public void testGetSetSupplier() {
        SupplierWithoutContactsDto supplier = new SupplierWithoutContactsDto();
        supplierContactWithSupplierDto.setSupplier(supplier);
        assertSame(supplier, supplierContactWithSupplierDto.getSupplier());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplierContactWithSupplierDto.setCreated(created);
        assertSame(created, supplierContactWithSupplierDto.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierContactWithSupplierDto.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierContactWithSupplierDto.getLastUpdated());
    }
    
}
