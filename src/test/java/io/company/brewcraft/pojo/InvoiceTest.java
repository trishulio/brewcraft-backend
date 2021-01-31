package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceTest {

    private Invoice invoice;

    @BeforeEach
    public void init() {
        invoice = new Invoice();
    }

    @Test
    public void testAllArgsConstructor() {
        invoice = new Invoice(
            12345L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatus(4L, "FINAL"),
            List.of(new InvoiceItem()),
            1
        );

        assertEquals(12345L, invoice.getId());
        assertEquals("ABCDE-12345", invoice.getInvoiceNumber());
        assertEquals("desc1", invoice.getDescription());
        assertEquals(new PurchaseOrder(1L), invoice.getPurchaseOrder());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), invoice.getPaymentDueDate());
        assertEquals(new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))), invoice.getFreight());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), invoice.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), invoice.getLastUpdated());
        assertEquals(new InvoiceStatus(4L, "FINAL"), invoice.getStatus());
        assertEquals(Money.parse("CAD 20"), invoice.getAmount());
        assertEquals(new Tax(Money.parse("CAD 6")), invoice.getTax());
        assertEquals(1, invoice.getItems().size());
        assertEquals(new InvoiceItem(), invoice.getItems().get(0));
    }

    @Test
    public void testAccessId() {
        assertNull(invoice.getId());
        invoice.setId(2L);
        assertEquals(2L, invoice.getId());
    }

    @Test
    public void testAccessInvoiceNumber() {
        assertNull(invoice.getInvoiceNumber());
        invoice.setInvoiceNumber("ABC-123");
        assertEquals("ABC-123", invoice.getInvoiceNumber());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(invoice.getPurchaseOrder());
        invoice.setPurchaseOrder(new PurchaseOrder(1L));
        assertEquals(new PurchaseOrder(1L), invoice.getPurchaseOrder());
    }

    @Test
    public void testAccessDescription() {
        assertNull(invoice.getDescription());
        invoice.setDescription("Description 1");
        assertEquals("Description 1", invoice.getDescription());
    }

    @Test
    public void testAccessGeneratedOn() {
        assertNull(invoice.getGeneratedOn());
        invoice.setGeneratedOn(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), invoice.getGeneratedOn());
    }

    @Test
    public void testAccessReceivedOn() {
        assertNull(invoice.getReceivedOn());
        invoice.setReceivedOn(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), invoice.getReceivedOn());
    }

    @Test
    public void testAccessPaymentDueDate() {
        assertNull(invoice.getPaymentDueDate());
        invoice.setPaymentDueDate(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), invoice.getPaymentDueDate());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(invoice.getLastUpdated());
        invoice.setLastUpdated(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), invoice.getLastUpdated());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(invoice.getCreatedAt());
        invoice.setCreatedAt(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), invoice.getCreatedAt());
    }

    @Test
    public void testAccessStatus() {
        assertNull(invoice.getStatus());
        invoice.setStatus(new InvoiceStatus(1L, "FINAL"));
        assertEquals(new InvoiceStatus(1L, "FINAL"), invoice.getStatus());
    }

    @Test
    public void testAccessFreight() {
        assertNull(invoice.getFreight());
        invoice.setFreight(new Freight(Money.parse("CAD 10")));
        assertEquals(new Freight(Money.parse("CAD 10")), invoice.getFreight());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoice.getVersion());
        invoice.setVersion(1);
        assertEquals(1, invoice.getVersion());
    }

    @Test
    public void testAccessItems() {
        assertNull(invoice.getItems());
        invoice.setItems(List.of(new InvoiceItem(2L)));
        assertEquals(List.of(new InvoiceItem(2L)), invoice.getItems());
    }

    @Test
    public void testGetAmount_ReturnsTotalOfAllItemsAmount() {
        InvoiceItem item1 = spy(new InvoiceItem());
        doReturn(Money.parse("CAD 100")).when(item1).getAmount();

        InvoiceItem item2 = spy(new InvoiceItem());
        doReturn(Money.parse("CAD 200")).when(item2).getAmount();

        invoice.setItems(List.of(item1, item2));
        assertEquals(Money.parse("CAD 300"), invoice.getAmount());
    }

    @Test
    public void testGetTax_ReturnsTotalOfAllItemsTax() {
        InvoiceItem item1 = spy(new InvoiceItem());
        doReturn(new Tax(Money.parse("CAD 100"))).when(item1).getTax();

        InvoiceItem item2 = spy(new InvoiceItem());
        doReturn(new Tax(Money.parse("CAD 200"))).when(item2).getTax();

        invoice.setItems(List.of(item1, item2));
        assertEquals(new Tax(Money.parse("CAD 300")), invoice.getTax());
    }
}
