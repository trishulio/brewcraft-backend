package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceStatus;

public class InvoiceStatusTest {

    private InvoiceStatus status;

    @BeforeEach
    public void init() {
        status = new InvoiceStatus();
    }

    @Test
    public void testAllArgConstructor() {
        status = new InvoiceStatus("FINAL");
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
