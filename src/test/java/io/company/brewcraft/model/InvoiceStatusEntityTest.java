package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceStatusEntityTest {

    private InvoiceStatusEntity status;

    @BeforeEach
    public void init() {
        status = new InvoiceStatusEntity();
    }

    @Test
    public void testIdArgConstructor() {
        status = new InvoiceStatusEntity(1L);
        assertEquals(1L, status.getId());
    }

    @Test
    public void testAllArgConstructor() {
        status = new InvoiceStatusEntity(2L, "FINAL");
        assertEquals(2L, status.getId());
        assertEquals("FINAL", status.getName());
    }

    @Test
    public void testAccessId() {
        assertNull(status.getId());
        status.setId(2L);
        assertEquals(2L, status.getId());
    }

    @Test
    public void testAccessName() {
        assertNull(status.getName());
        status.setName("PENDING");
        assertEquals("PENDING", status.getName());
    }
}
