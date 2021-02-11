package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceDtoTest {

    InvoiceDto invoice;

    @BeforeEach
    public void init() {
        invoice = new InvoiceDto();
    }

    @Test
    public void testAllArgsConstructor() {
        invoice = new InvoiceDto(
            1L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrderDto(2L),
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            new MoneyDto("CAD", new BigDecimal("10")),
            new TaxDto(),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatusDto("FINAL"),
            List.of(new InvoiceItemDto()),
            1
        );

        assertEquals(1L, invoice.getId());
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(new PurchaseOrderDto(2L), invoice.getPurchaseOrder());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
        assertEquals(new MoneyDto("CAD", new BigDecimal("10")), invoice.getAmount());
        assertEquals(new TaxDto(), invoice.getTax());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getLastUpdated());
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getCreatedAt());
        assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
        assertEquals(1, invoice.getItems().size());
        assertEquals(new InvoiceItemDto(), invoice.getItems().get(0));
    }

    @Test
    public void testAccessId() {
        assertNull(invoice.getId());
        invoice.setId(1L);
        assertEquals(1L, invoice.getId());
    }

    @Test
    public void testAccessInvoiceNumber() {
        assertNull(invoice.getInvoiceNumber());
        invoice.setInvoiceNumber("ABCDE-12345");
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(invoice.getPurchaseOrder());
        invoice.setPurchaseOrder(new PurchaseOrderDto(3L));
        assertEquals(new PurchaseOrderDto(3L), invoice.getPurchaseOrder());
    }

    @Test
    public void testAccessDescription() {
        assertNull(invoice.getDescription());
        invoice.setDescription("desc1");
        assertEquals("desc1", invoice.getDescription());
    }

    @Test
    public void testAccessFreight() {
        assertNull(invoice.getFreight());
        invoice.setFreight(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))));
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
    }

    @Test
    public void testAccessAmount() {
        assertNull(invoice.getAmount());
        invoice.setAmount(new MoneyDto("CAD", new BigDecimal("10")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("10")), invoice.getAmount());
    }

    @Test
    public void testAccessTax() {
        assertNull(invoice.getTax());
        invoice.setTax(new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))));
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getTax());
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
        invoice.setReceivedOn(LocalDateTime.of(2000, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
    }

    @Test
    public void testAccessPaymentDueDate() {
        assertNull(invoice.getPaymentDueDate());
        invoice.setPaymentDueDate(LocalDateTime.of(2001, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(invoice.getLastUpdated());
        invoice.setLastUpdated(LocalDateTime.of(2002, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getLastUpdated());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(invoice.getCreatedAt());
        invoice.setCreatedAt(LocalDateTime.of(2003, 1, 1, 12, 0));
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getCreatedAt());
    }

    @Test
    public void testAccessStatus() {
        assertNull(invoice.getStatus());
        invoice.setStatus(new InvoiceStatusDto("FINAL"));
        assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
    }

    @Test
    public void testAccessItems() {
        assertNull(invoice.getItems());
        invoice.setItems(List.of(new InvoiceItemDto()));
        assertEquals(List.of(new InvoiceItemDto()), invoice.getItems());
    }
}
