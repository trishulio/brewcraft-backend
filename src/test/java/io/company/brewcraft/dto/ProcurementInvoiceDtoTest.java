package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;

public class ProcurementInvoiceDtoTest {
    ProcurementInvoiceDto invoice;

    @BeforeEach
    public void init() {
        invoice = new ProcurementInvoiceDto();
    }

    @Test
    public void testAllArgsConstructor() {
        invoice = new ProcurementInvoiceDto(
            1L,
            "ABCDE-12345",
            "desc1",
            new ProcurementPurchaseOrderDto(1L),
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            new AmountDto(new MoneyDto("CAD", new BigDecimal("10"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatusDto(99L),
            1
        );

        assertEquals(1L, invoice.getId());
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(new ProcurementPurchaseOrderDto(1L), invoice.getPurchaseOrder());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
        assertEquals(new AmountDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getAmount());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
        assertEquals(new InvoiceStatusDto(99L), invoice.getInvoiceStatus());
        assertEquals(1, invoice.getVersion());
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
    public void testAccessDescription() {
        assertNull(invoice.getDescription());
        invoice.setDescription("desc1");
        assertEquals("desc1", invoice.getDescription());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(invoice.getPurchaseOrder());
        invoice.setPurchaseOrder(new ProcurementPurchaseOrderDto(1L));
        assertEquals(new ProcurementPurchaseOrderDto(1L), invoice.getPurchaseOrder());
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
        invoice.setAmount(new AmountDto(new MoneyDto("CAD", new BigDecimal("10"))));
        assertEquals(new AmountDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getAmount());
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
        assertNull(invoice.getInvoiceStatus());
        invoice.setInvoiceStatus(new InvoiceStatusDto(99L));
        assertEquals(new InvoiceStatusDto(99L), invoice.getInvoiceStatus());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoice.getVersion());
        invoice.setVersion(99);
        assertEquals(99, invoice.getVersion());
    }
}
