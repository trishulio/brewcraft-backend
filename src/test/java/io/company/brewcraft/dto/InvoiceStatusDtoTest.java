package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceStatusDtoTest {

    private InvoiceStatusDto invoiceStatus;

    @BeforeEach
    public void init() {
        invoiceStatus = new InvoiceStatusDto();
    }

    @Test
    public void testIdArgConstructor() {
        invoiceStatus = new InvoiceStatusDto(99L);
        assertEquals(99L, invoiceStatus.getId());
    }

    @Test
    public void testAllArgConstructor() {
        invoiceStatus = new InvoiceStatusDto(99L, "FINAL", LocalDateTime.of(1999, 12, 12, 0, 0), LocalDateTime.of(2000, 12, 12, 0, 0), 1);

        assertEquals(99L, invoiceStatus.getId());
        assertEquals("FINAL", invoiceStatus.getName());
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), invoiceStatus.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), invoiceStatus.getLastUpdated());
        assertEquals(1, invoiceStatus.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(invoiceStatus.getId());
        invoiceStatus.setId(1L);
        assertEquals(1L, invoiceStatus.getId());
    }

    @Test
    public void testAccessName() {
        assertNull(invoiceStatus.getName());
        invoiceStatus.setName("STATUS_NAME");
        assertEquals("STATUS_NAME", invoiceStatus.getName());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(invoiceStatus.getCreatedAt());
        invoiceStatus.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), invoiceStatus.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(invoiceStatus.getLastUpdated());
        invoiceStatus.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), invoiceStatus.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoiceStatus.getVersion());
        invoiceStatus.setVersion(1);
        assertEquals(1, invoiceStatus.getVersion());
    }
}
