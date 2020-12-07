package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceEntityTest {

    private InvoiceEntity invoice;

    @BeforeEach
    public void init() {
        invoice = new InvoiceEntity();
    }

    @Test
    public void testAllArgConstructor() {
        InvoiceItemEntity item = new InvoiceItemEntity(12345L);
        invoice = new InvoiceEntity(12345L, new Supplier(), LocalDateTime.MAX, LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), InvoiceStatus.FINAL, List.of(item), 1);
        assertEquals(12345L, invoice.getId());
        assertEquals(new Supplier(), invoice.getSupplier());
        assertEquals(LocalDateTime.MAX, invoice.getDate());
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
        invoice.setDate(LocalDateTime.MAX);
        assertEquals(LocalDateTime.MAX, invoice.getDate());
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
        InvoiceItemEntity item = new InvoiceItemEntity(12345L);
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
