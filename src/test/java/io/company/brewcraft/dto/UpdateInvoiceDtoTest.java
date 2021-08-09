package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateInvoiceDtoTest {

    UpdateInvoiceDto invoice;

    @BeforeEach
    public void init() {
        invoice = new UpdateInvoiceDto();
    }

    @Test
    public void testAllArgsConstructor() {
        invoice = new UpdateInvoiceDto(
            "ABCDE-12345",
            2L,
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            99L,
            List.of(new UpdateInvoiceItemDto()),
            1
        );

        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals(2L, invoice.getPurchaseOrderId());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
        assertEquals(99L, invoice.getStatusId());
        assertEquals(1, invoice.getItems().size());
        assertEquals(new UpdateInvoiceItemDto(), invoice.getItems().get(0));
    }

    @Test
    public void testAccessInvoiceNumber() {
        assertNull(invoice.getInvoiceNumber());
        invoice.setInvoiceNumber("ABCDE-12345");
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
    }

    @Test
    public void testAccessPurchaseOrderId() {
        assertNull(invoice.getPurchaseOrderId());
        invoice.setPurchaseOrderId(2L);
        assertEquals(2L, invoice.getPurchaseOrderId());
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
        assertNull(invoice.getStatusId());
        invoice.setStatusId(99L);
        assertEquals(99L, invoice.getStatusId());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoice.getVersion());
        invoice.setVersion(3);
        assertEquals(3, invoice.getVersion());
    }

    @Test
    public void testAccessItems() {
        assertNull(invoice.getItems());
        invoice.setItems(List.of(new UpdateInvoiceItemDto()));
        assertEquals(List.of(new UpdateInvoiceItemDto()), invoice.getItems());
    }
}
