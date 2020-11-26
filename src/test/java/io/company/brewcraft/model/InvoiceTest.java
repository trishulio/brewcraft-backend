package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceTest {

    private Invoice invoice;

    @BeforeEach
    public void init() {
        invoice = new Invoice();
    }

    @Test
    public void testAllArgConstructor() {
        InvoiceItem item = new InvoiceItem(12345L);
        invoice = new Invoice(12345L, new Supplier(), new Date(12345), InvoiceStatus.FINAL, List.of(item), 1);
        assertEquals(12345L, invoice.getId());
        assertEquals(new Supplier(), invoice.getSupplier());
        assertEquals(new Date(12345), invoice.getDate());
        assertEquals(InvoiceStatus.FINAL, invoice.getStatus());
        assertEquals(List.of(item), invoice.getItems());
        assertEquals(1, invoice.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(invoice.getId());
        invoice.setId(12345L);
        assertEquals(12345L, invoice.getId());
    }

    @Test
    public void testAccessSupplier() {
        assertNull(invoice.getSupplier());
        invoice.setSupplier(new Supplier());
        assertEquals(new Supplier(), invoice.getSupplier());
    }

    @Test
    public void testAccessDate() {
        assertNull(invoice.getDate());
        invoice.setDate(new Date(12345));
        assertEquals(new Date(12345), invoice.getDate());
    }

    @Test
    public void testAccessStatus() {
        assertNull(invoice.getStatus());
        invoice.setStatus(InvoiceStatus.PENDING);
        assertSame(InvoiceStatus.PENDING, invoice.getStatus());
    }

    @Test
    public void testAccessItems() {
        assertNull(invoice.getItems());
        InvoiceItem item = new InvoiceItem(12345L);
        invoice.setItems(List.of(item));
        assertEquals(List.of(item), invoice.getItems());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoice.getVersion());
        invoice.setVersion(12345);
        assertEquals(12345, invoice.getVersion());
    }
}
