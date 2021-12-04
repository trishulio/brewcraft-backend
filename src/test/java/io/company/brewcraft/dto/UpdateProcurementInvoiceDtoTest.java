package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceDto;

public class UpdateProcurementInvoiceDtoTest {
    UpdateProcurementInvoiceDto invoice;

    @BeforeEach
    public void init() {
        invoice = new UpdateProcurementInvoiceDto();
    }

    @Test
    public void testAllArgsConstructor() {
        invoice = new UpdateProcurementInvoiceDto(
            1L,
            "ABCDE-12345",
            2L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L,
            1
        );

        assertEquals(1L, invoice.getId());
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals(2L, invoice.getPurchaseOrderId());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
        assertEquals(99L, invoice.getInvoiceStatusId());
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        UpdateProcurementInvoiceDto dto = new UpdateProcurementInvoiceDto(1L);

        assertEquals(1L, dto.getId());
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
    public void testAccessPurchaseOrderId() {
        assertNull(invoice.getPurchaseOrderId());
        invoice.setPurchaseOrderId(1L);
        assertEquals(1L, invoice.getPurchaseOrderId());
    }

    @Test
    public void testAccessFreight() {
        assertNull(invoice.getFreight());
        invoice.setFreight(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))));
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
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
    public void testAccessStatusId() {
        assertNull(invoice.getInvoiceStatusId());
        invoice.setInvoiceStatusId(99L);
        assertEquals(99L, invoice.getInvoiceStatusId());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoice.getVersion());
        invoice.setVersion(3);
        assertEquals(3, invoice.getVersion());
    }
}
