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
    public void testIdConstructor_SetsIdValue() {
        invoice = new InvoiceEntity(11L);
        assertEquals(11L, invoice.getId());
    }

    @Test
    public void testAllArgConstructor() {
        InvoiceItemEntity invoiceItem = new InvoiceItemEntity(3L, "desc2", null, new QuantityEntity(8L), new MoneyEntity(9L), new TaxEntity(10L), new MaterialEntity(7L, null, null, null, null, null, null, null, null), 1);
        InvoiceEntity invoice = new InvoiceEntity(1L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrderEntity(2L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new FreightEntity(6L),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatusEntity(4L, "FINAL"),
            List.of(invoiceItem),
            1
        );

        assertEquals(1L, invoice.getId());
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(new PurchaseOrderEntity(2L), invoice.getPurchaseOrder());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(new FreightEntity(6L), invoice.getFreight());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
        assertEquals(new InvoiceStatusEntity(4L, "FINAL"), invoice.getStatus());
        assertEquals(1, invoice.getVersion());
        assertEquals(1, invoice.getItems().size());
        InvoiceItemEntity item = invoice.getItems().get(0);
        assertEquals(3L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(invoice, item.getInvoice());
        assertEquals(new QuantityEntity(8L), item.getQuantity());
        assertEquals(new MoneyEntity(9L), item.getPrice());
        assertEquals(new TaxEntity(10L), item.getTax());
        assertEquals(new MaterialEntity(7L, null, null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(invoice.getId());
        invoice.setId(12345L);
        assertEquals(12345L, invoice.getId());
    }

    @Test
    public void testAccessDescription() {
        assertNull(invoice.getDescription());
        invoice.setDescription("Description 1");
        assertEquals("Description 1", invoice.getDescription());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(invoice.getPurchaseOrder());
        invoice.setPurchaseOrder(new PurchaseOrderEntity(1L));
        assertEquals(new PurchaseOrderEntity(1L), invoice.getPurchaseOrder());
    }

    @Test
    public void testAccessGeneratedOn() {
        assertNull(invoice.getGeneratedOn());
        invoice.setGeneratedOn(LocalDateTime.of(1999, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
    }

    @Test
    public void testAccessReceivedOn() {
        assertNull(invoice.getReceivedOn());
        invoice.setReceivedOn(LocalDateTime.of(1999, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getReceivedOn());
    }

    @Test
    public void testAccessPaymentDueDate() {
        assertNull(invoice.getPaymentDueDate());
        invoice.setPaymentDueDate(LocalDateTime.of(1999, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getPaymentDueDate());
    }

    @Test
    public void testAccessFreight() {
        assertNull(invoice.getFreight());
        invoice.setFreight(new FreightEntity(1L));
        assertEquals(new FreightEntity(1L), invoice.getFreight());
    }

    @Test
    public void testAccessStatus() {
        assertNull(invoice.getStatus());
        invoice.setStatus(new InvoiceStatusEntity(2L));
        assertEquals(new InvoiceStatusEntity(2L), invoice.getStatus());
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
