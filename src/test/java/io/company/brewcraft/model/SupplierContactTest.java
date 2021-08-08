package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class SupplierContactTest {

    private SupplierContact supplierContact;

    @BeforeEach
    public void init() {
        supplierContact = new SupplierContact();
    }

    @Test
    public void testAllArgConstructor() {
        supplierContact = new SupplierContact(1L, new Supplier(1L), "FIRST_NAME", "LAST_NAME", "POSITION", "EMAIL", "PHONE_NUMBER", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);

        assertEquals(1L, supplierContact.getId());
        assertEquals(new Supplier(1L), supplierContact.getSupplier());
        assertEquals("FIRST_NAME", supplierContact.getFirstName());
        assertEquals("LAST_NAME", supplierContact.getLastName());
        assertEquals("POSITION", supplierContact.getPosition());
        assertEquals("EMAIL", supplierContact.getEmail());
        assertEquals("PHONE_NUMBER", supplierContact.getPhoneNumber());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), supplierContact.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), supplierContact.getLastUpdated());
        assertEquals(1, supplierContact.getVersion());
    }

    @Test
    public void testIdArgConstructor() {
        supplierContact = new SupplierContact(1L);

        assertEquals(1L, supplierContact.getId());
    }

    @Test
    public void testGetSetId() {
        supplierContact.setId(1L);
        assertSame(1L, supplierContact.getId());
    }

    @Test
    public void testGetSetFirstName() {
        supplierContact.setFirstName("firstNameTest");
        assertSame("firstNameTest", supplierContact.getFirstName());
    }

    @Test
    public void testGetSetLastName() {
        supplierContact.setLastName("testName");
        assertSame("testName", supplierContact.getLastName());
    }

    @Test
    public void testGetSetEmail() {
        supplierContact.setEmail("testEmail");
        assertSame("testEmail", supplierContact.getEmail());
    }

    @Test
    public void testGetSetPhoneNumber() {
        supplierContact.setPhoneNumber("testNumber");
        assertSame("testNumber", supplierContact.getPhoneNumber());
    }

    @Test
    public void testGetSetPosition() {
        supplierContact.setPosition("position");
        assertSame("position", supplierContact.getPosition());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        supplierContact.setCreatedAt(created);
        assertSame(created, supplierContact.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        supplierContact.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, supplierContact.getLastUpdated());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        supplierContact = new SupplierContact(1L, new Supplier(1L), "FIRST_NAME", "LAST_NAME", "POSITION", "EMAIL", "PHONE_NUMBER", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);

        final String json = "{\"id\":1,\"supplier\":{\"id\":1,\"name\":null,\"address\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"firstName\":\"FIRST_NAME\",\"lastName\":\"LAST_NAME\",\"position\":\"POSITION\",\"email\":\"EMAIL\",\"phoneNumber\":\"PHONE_NUMBER\",\"createdAt\":{\"nano\":0,\"year\":2000,\"monthValue\":1,\"dayOfMonth\":1,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"SATURDAY\",\"dayOfYear\":1,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2001,\"monthValue\":1,\"dayOfMonth\":1,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"MONDAY\",\"dayOfYear\":1,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        JSONAssert.assertEquals(json, supplierContact.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
