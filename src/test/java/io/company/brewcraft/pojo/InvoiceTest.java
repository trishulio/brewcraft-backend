package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        assertEquals(new InvoiceStatus(99L), this.invoice.getStatus());
        assertNull(this.invoice.getAmount());
        assertNull(this.invoice.getTax());
        assertEquals(1, this.invoice.getItems().size());
        final InvoiceItem item = new InvoiceItem();
        item.setInvoice(this.invoice);
        assertEquals(item, this.invoice.getItems().iterator().next());
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
        assertNull(this.invoice.getStatus());
        this.invoice.setStatus(new InvoiceStatus(99L));
        assertEquals(new InvoiceStatus(99L), this.invoice.getStatus());
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
        assertNull(this.invoice.getItems());
        final InvoiceItem item = new InvoiceItem(2L);
        this.invoice.setItems(List.of(item));

        final InvoiceItem expected = new InvoiceItem(2L);
        expected.setInvoice(new Invoice());

        assertEquals(List.of(expected), this.invoice.getItems());
        assertEquals(this.invoice, item.getInvoice());
    }

    @Test
    public void testAccessItems_SetsNull_WhenInputIsNull() {
        this.invoice.setItems(null);
        assertNull(this.invoice.getItems());
    }

    @Test
    public void testGetAmount_ReturnsTotalOfAllItemsAmount() {
        final InvoiceItem item1 = spy(new InvoiceItem());
        item1.setPrice(Money.parse("CAD 10"));
        item1.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        final InvoiceItem item2 = spy(new InvoiceItem());
        item2.setPrice(Money.parse("CAD 20"));
        item2.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        this.invoice.setItems(List.of(item1, item2));
        assertEquals(Money.parse("CAD 300"), this.invoice.getAmount());
    }

    @Test
    public void testGetAmount_ReturnsAmountOfAddedItem() {
        final InvoiceItem item1 = spy(new InvoiceItem());
        item1.setPrice(Money.parse("CAD 20"));
        item1.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        this.invoice.addItem(item1);
        assertEquals(Money.parse("CAD 200"), this.invoice.getAmount());
    }

    @Test
    public void testGetAmount_ReturnsAmountReducedByRemovedItem() {
        final InvoiceItem item1 = spy(new InvoiceItem());
        item1.setPrice(Money.parse("CAD 10"));
        item1.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        final InvoiceItem item2 = spy(new InvoiceItem());
        item2.setPrice(Money.parse("CAD 20"));
        item2.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));

        this.invoice.setItems(List.of(item1, item2));
        
        this.invoice.removeItem(item1);
        
        assertEquals(Money.parse("CAD 200"), this.invoice.getAmount());
    }

    @Test
    public void testGetTax_ReturnsTotalOfAllItemsTax() {
        final InvoiceItem item1 = spy(new InvoiceItem());
        doReturn(new Tax(Money.parse("CAD 100"))).when(item1).getTax();

        final InvoiceItem item2 = spy(new InvoiceItem());
        doReturn(new Tax(Money.parse("CAD 200"))).when(item2).getTax();

        this.invoice.setItems(List.of(item1, item2));
        assertEquals(new Tax(Money.parse("CAD 300")), this.invoice.getTax());
    }

    @Test
    public void testAddItem_CreatesNewItemList_WhenItemIsNotNull() {
        assertNull(this.invoice.getItems());

        final InvoiceItem item = new InvoiceItem(1L);
        assertNull(item.getInvoice());

        this.invoice.addItem(item);

        assertEquals(List.of(item), this.invoice.getItems());
        assertEquals(this.invoice, item.getInvoice());
    }

    @Test
    public void testAddItem_AddsItemsToList_WhenItemIsNotNull() {
        final InvoiceItem existing = new InvoiceItem(0L);
        this.invoice.setItems(List.of(existing));
        assertEquals(List.of(existing), this.invoice.getItems());

        final InvoiceItem item = new InvoiceItem(1L);
        assertNull(item.getInvoice());

        this.invoice.addItem(item);

        assertEquals(List.of(existing, item), this.invoice.getItems());
        assertEquals(this.invoice, existing.getInvoice());
        assertEquals(this.invoice, item.getInvoice());
    }

    @Test
    public void testAddItem_AddsItemOnlyOnce_WhenMultipleAdditionsArePerformed() {
        final InvoiceItem item = new InvoiceItem(1L);
        assertNull(item.getInvoice());

        this.invoice.addItem(item);
        this.invoice.addItem(item);
        this.invoice.addItem(item);

        assertEquals(List.of(item), this.invoice.getItems());
        assertEquals(this.invoice, item.getInvoice());
    }

    @Test
    public void testRemoveItem_ReturnsFalse_WhenListIsNull() {
        assertFalse(this.invoice.removeItem(new InvoiceItem(1L)));
    }

    @Test
    public void testRemoveItem_ReturnsFalse_WhenListIsEmpty() {
        this.invoice.setItems(new ArrayList<>());
        assertFalse(this.invoice.removeItem(new InvoiceItem(1L)));
    }

    @Test
    public void testRemoveItem_ReturnsFalse_WhenItemExistInList() {
        this.invoice.setItems(List.of(new InvoiceItem(2L)));

        assertFalse(this.invoice.removeItem(new InvoiceItem(1L)));
    }

    @Test
    public void testRemoveItem_ReturnsTrueAndUpdatesItemInvoice_WhenItemExist() {
        final InvoiceItem item = new InvoiceItem(1L);
        assertNull(item.getInvoice());

        this.invoice.addItem(item);
        assertEquals(List.of(item), this.invoice.getItems());
        assertEquals(this.invoice, item.getInvoice());

        assertTrue(this.invoice.removeItem(item));
        assertNull(item.getInvoice());
    }
}
