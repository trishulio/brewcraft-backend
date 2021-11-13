package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Tax;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class InvoiceTest {

    private Invoice invoice;

    @BeforeEach
    public void init() {
        this.invoice = new Invoice();
    }

    @Test
    public void testAllArgsConstructor() {
        this.invoice = new Invoice(12345L,
            "ABCDE-12345",
            "desc1",
            new PurchaseOrder(1L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            LocalDateTime.of(2003, 1, 1, 12, 0),
            new InvoiceStatus(99L),
            List.of(new InvoiceItem()),
            1
        );

        assertEquals(12345L, this.invoice.getId());
        assertEquals("ABCDE-12345", this.invoice.getInvoiceNumber());
        assertEquals("desc1", this.invoice.getDescription());
        assertEquals(new PurchaseOrder(1L), this.invoice.getPurchaseOrder());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), this.invoice.getGeneratedOn());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), this.invoice.getReceivedOn());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), this.invoice.getPaymentDueDate());
        assertEquals(new Freight(Money.of(CurrencyUnit.CAD, new BigDecimal("3"))), this.invoice.getFreight());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), this.invoice.getCreatedAt());
        assertEquals(LocalDateTime.of(2003, 1, 1, 12, 0), this.invoice.getLastUpdated());
        assertEquals(new InvoiceStatus(99L), this.invoice.getInvoiceStatus());
        assertNull(this.invoice.getAmount());
        assertNull(this.invoice.getTax());
        assertEquals(1, this.invoice.getInvoiceItems().size());
        final InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoice(this.invoice);
        assertEquals(invoiceItem, this.invoice.getInvoiceItems().iterator().next());
    }

    @Test
    public void testAccessId() {
        assertNull(this.invoice.getId());
        this.invoice.setId(2L);
        assertEquals(2L, this.invoice.getId());
    }

    @Test
    public void testAccessInvoiceNumber() {
        assertNull(this.invoice.getInvoiceNumber());
        this.invoice.setInvoiceNumber("ABC-123");
        assertEquals("ABC-123", this.invoice.getInvoiceNumber());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(this.invoice.getPurchaseOrder());
        this.invoice.setPurchaseOrder(new PurchaseOrder(1L));
        assertEquals(new PurchaseOrder(1L), this.invoice.getPurchaseOrder());
    }

    @Test
    public void testAccessDescription() {
        assertNull(this.invoice.getDescription());
        this.invoice.setDescription("Description 1");
        assertEquals("Description 1", this.invoice.getDescription());
    }

    @Test
    public void testAccessGeneratedOn() {
        assertNull(this.invoice.getGeneratedOn());
        this.invoice.setGeneratedOn(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), this.invoice.getGeneratedOn());
    }

    @Test
    public void testAccessReceivedOn() {
        assertNull(this.invoice.getReceivedOn());
        this.invoice.setReceivedOn(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), this.invoice.getReceivedOn());
    }

    @Test
    public void testAccessPaymentDueDate() {
        assertNull(this.invoice.getPaymentDueDate());
        this.invoice.setPaymentDueDate(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), this.invoice.getPaymentDueDate());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(this.invoice.getLastUpdated());
        this.invoice.setLastUpdated(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), this.invoice.getLastUpdated());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(this.invoice.getCreatedAt());
        this.invoice.setCreatedAt(LocalDateTime.of(1999, 1, 1, 10, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 10, 0), this.invoice.getCreatedAt());
    }

    @Test
    public void testAccessStatus() {
        assertNull(this.invoice.getInvoiceStatus());
        this.invoice.setInvoiceStatus(new InvoiceStatus(99L));
        assertEquals(new InvoiceStatus(99L), this.invoice.getInvoiceStatus());
    }

    @Test
    public void testAccessFreight() {
        assertNull(this.invoice.getFreight());
        this.invoice.setFreight(new Freight(Money.parse("CAD 10")));
        assertEquals(new Freight(Money.parse("CAD 10")), this.invoice.getFreight());
    }

    @Test
    public void testAccessVersion() {
        assertNull(this.invoice.getVersion());
        this.invoice.setVersion(1);
        assertEquals(1, this.invoice.getVersion());
    }

    @Test
    public void testAccessItems() {
        assertNull(this.invoice.getInvoiceItems());
        final InvoiceItem invoiceItem = new InvoiceItem(2L);
        this.invoice.setInvoiceItems(List.of(invoiceItem));

        final InvoiceItem expected = new InvoiceItem(2L);
        expected.setInvoice(new Invoice());

        assertEquals(List.of(expected), this.invoice.getInvoiceItems());
        assertEquals(this.invoice, invoiceItem.getInvoice());
    }

    @Test
    public void testAccessItems_SetsNull_WhenInputIsNull() {
        this.invoice.setInvoiceItems(null);
        assertNull(this.invoice.getInvoiceItems());
    }

    @Test
    public void testGetAmount_ReturnsTotalOfAllItemsAmount() {
        final InvoiceItem invoiceItem1 = spy(new InvoiceItem());
        invoiceItem1.setPrice(Money.parse("CAD 10"));
        invoiceItem1.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        final InvoiceItem invoiceItem2 = spy(new InvoiceItem());
        invoiceItem2.setPrice(Money.parse("CAD 20"));
        invoiceItem2.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        this.invoice.setInvoiceItems(List.of(invoiceItem1, invoiceItem2));
        assertEquals(Money.parse("CAD 300"), this.invoice.getAmount());
    }

    @Test
    public void testGetAmount_ReturnsAmountOfAddedItem() {
        final InvoiceItem invoiceItem1 = spy(new InvoiceItem());
        invoiceItem1.setPrice(Money.parse("CAD 20"));
        invoiceItem1.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        this.invoice.addItem(invoiceItem1);
        assertEquals(Money.parse("CAD 200"), this.invoice.getAmount());
    }

    @Test
    public void testGetAmount_ReturnsAmountReducedByRemovedItem() {
        final InvoiceItem invoiceItem1 = spy(new InvoiceItem());
        invoiceItem1.setPrice(Money.parse("CAD 10"));
        invoiceItem1.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        final InvoiceItem invoiceItem2 = spy(new InvoiceItem());
        invoiceItem2.setPrice(Money.parse("CAD 20"));
        invoiceItem2.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        this.invoice.setInvoiceItems(List.of(invoiceItem1, invoiceItem2));

        this.invoice.removeItem(invoiceItem1);

        assertEquals(Money.parse("CAD 200"), this.invoice.getAmount());
    }

    @Test
    public void testGetTax_ReturnsTotalOfAllItemsTax() {
        final InvoiceItem invoiceItem1 = spy(new InvoiceItem());
        doReturn(new Tax(Money.parse("CAD 100"))).when(invoiceItem1).getTax();

        final InvoiceItem invoiceItem2 = spy(new InvoiceItem());
        doReturn(new Tax(Money.parse("CAD 200"))).when(invoiceItem2).getTax();

        this.invoice.setInvoiceItems(List.of(invoiceItem1, invoiceItem2));
        assertEquals(new Tax(Money.parse("CAD 300")), this.invoice.getTax());
    }

    @Test
    public void testAddItem_CreatesNewItemList_WhenItemIsNotNull() {
        assertNull(this.invoice.getInvoiceItems());

        final InvoiceItem invoiceItem = new InvoiceItem(1L);
        assertNull(invoiceItem.getInvoice());

        this.invoice.addItem(invoiceItem);

        assertEquals(List.of(invoiceItem), this.invoice.getInvoiceItems());
        assertEquals(this.invoice, invoiceItem.getInvoice());
    }

    @Test
    public void testAddItem_AddsItemsToList_WhenItemIsNotNull() {
        final InvoiceItem existing = new InvoiceItem(0L);
        this.invoice.setInvoiceItems(List.of(existing));
        assertEquals(List.of(existing), this.invoice.getInvoiceItems());

        final InvoiceItem invoiceItem = new InvoiceItem(1L);
        assertNull(invoiceItem.getInvoice());

        this.invoice.addItem(invoiceItem);

        assertEquals(List.of(existing, invoiceItem), this.invoice.getInvoiceItems());
        assertEquals(this.invoice, existing.getInvoice());
        assertEquals(this.invoice, invoiceItem.getInvoice());
    }

    @Test
    public void testAddItem_AddsItemOnlyOnce_WhenMultipleAdditionsArePerformed() {
        final InvoiceItem invoiceItem = new InvoiceItem(1L);
        assertNull(invoiceItem.getInvoice());

        this.invoice.addItem(invoiceItem);
        this.invoice.addItem(invoiceItem);
        this.invoice.addItem(invoiceItem);

        assertEquals(List.of(invoiceItem), this.invoice.getInvoiceItems());
        assertEquals(this.invoice, invoiceItem.getInvoice());
    }

    @Test
    public void testAddItem_DoesNothing_WhenItemIsNull() {
        invoice.addItem(null);
        assertNull(invoice.getInvoiceItems());
    }

    @Test
    public void testRemoveItem_ReturnsFalse_WhenListIsNull() {
        assertFalse(this.invoice.removeItem(new InvoiceItem(1L)));
    }

    @Test
    public void testRemoveItem_ReturnsFalse_WhenListIsEmpty() {
        this.invoice.setInvoiceItems(new ArrayList<>());
        assertFalse(this.invoice.removeItem(new InvoiceItem(1L)));
    }

    @Test
    public void testRemoveItem_ReturnsFalse_WhenItemExistInList() {
        this.invoice.setInvoiceItems(List.of(new InvoiceItem(2L)));

        assertFalse(this.invoice.removeItem(new InvoiceItem(1L)));
    }

    @Test
    public void testRemoveItem_ReturnsTrueAndUpdatesItemInvoice_WhenItemExist() {
        final InvoiceItem invoiceItem = new InvoiceItem(1L);
        assertNull(invoiceItem.getInvoice());

        this.invoice.addItem(invoiceItem);
        assertEquals(List.of(invoiceItem), this.invoice.getInvoiceItems());
        assertEquals(this.invoice, invoiceItem.getInvoice());

        assertTrue(this.invoice.removeItem(invoiceItem));
        assertNull(invoiceItem.getInvoice());
    }

    @Test
    public void testGetItemCount_Returns0_WhenItemsIsNull() {
        invoice.setInvoiceItems(null);

        assertEquals(0, invoice.getItemCount());
    }

    @Test
    public void testGetItemCount_Returns0_WhenItemsIsEmpty() {
        invoice.setInvoiceItems(List.of());

        assertEquals(0, invoice.getItemCount());
    }

    @Test
    public void testGetItemCount_ReturnsItemCount() {
        invoice.setInvoiceItems(List.of(new InvoiceItem()));

        assertEquals(1, invoice.getItemCount());
    }
}
