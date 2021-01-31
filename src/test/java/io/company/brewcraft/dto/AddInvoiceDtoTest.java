package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddInvoiceDtoTest {

    AddInvoiceDto invoice;

    @BeforeEach
    public void init() {
        invoice = new AddInvoiceDto();
    }

    @Test
    public void testAllArgsConstructor() {
        invoice = new AddInvoiceDto(
            "ABCDE-12345",
            "desc1",
            new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new InvoiceStatusDto("FINAL"),
            List.of(new UpdateInvoiceItemDto())
        );

        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10"))), invoice.getFreight());
        assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
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
    public void testAccessStatus() {
        assertNull(invoice.getStatus());
        invoice.setStatus(new InvoiceStatusDto("FINAL"));
        assertEquals(new InvoiceStatusDto("FINAL"), invoice.getStatus());
    }

    @Test
    public void testAccessItems() {
        assertNull(invoice.getItems());
        invoice.setItems(List.of(new UpdateInvoiceItemDto()));
        assertEquals(List.of(new UpdateInvoiceItemDto()), invoice.getItems());
    }
}
