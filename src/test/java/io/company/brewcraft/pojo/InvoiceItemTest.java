package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceItemTest {

    private InvoiceItem item;

    @BeforeEach
    public void init() {
        item = new InvoiceItem();
    }

    @Test
    public void testAllArgsConstructor_SetsValues() {
        InvoiceItem item = new InvoiceItem(2L,
                "desc2",
                Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
                Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
                new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
                new Material(7L),
                LocalDateTime.of(1999, 1, 1, 1, 1),
                LocalDateTime.of(1999, 1, 1, 1, 1),
                1);

        assertEquals(2L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(Money.of(CurrencyUnit.CAD, new BigDecimal("5")), item.getPrice());
        assertEquals(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), item.getTax());
        assertEquals(Money.parse("CAD 20"), item.getAmount());
        assertEquals(new Material(7L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testAccessInvoice() {
        Invoice invoice = new Invoice();

        item.setInvoice(invoice);
        assertEquals(invoice, item.getInvoice());
    }

    @Test
    public void testSetInvoice_RemovesItselfFromTheSourceInvoiceItemsAndSetsNewListOnTarget_WhenTargetInvoiceItemsAreNull() {
        Invoice source = new Invoice();
        source.setItems(List.of(item));

        Invoice target = new Invoice();
        item.setInvoice(target);

        assertEquals(List.of(), source.getItems());
        assertEquals(List.of(item), target.getItems());
    }

    @Test
    public void testSetInvoice_RemovesItemFromInvoiceAndAddsToExistingTargetInvoiceItemList_WhenTargetInvoiceItemsAreNotNull() {
        Invoice source = new Invoice();
        source.setItems(List.of(item));

        Invoice target = new Invoice();
        target.setItems(List.of(new InvoiceItem()));

        item.setInvoice(target);

        assertEquals(List.of(), source.getItems());

        Invoice expectedTarget = new Invoice();
        expectedTarget.setItems(List.of(new InvoiceItem(), new InvoiceItem()));
        assertEquals(expectedTarget, target);
    }

    @Test
    public void testAccessId() {
        assertNull(item.getId());
        item.setId(1L);
        assertEquals(1L, item.getId());
    }

    @Test
    public void testAccessDescription() {
        assertNull(item.getDescription());
        item.setDescription("Descriptio 1");
        assertEquals("Descriptio 1", item.getDescription());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(item.getQuantity());
        item.setQuantity(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(item.getPrice());
        item.setPrice(Money.parse("CAD 1"));
        assertEquals(Money.parse("CAD 1"), item.getPrice());
    }

    @Test
    public void testAccessTax() {
        assertNull(item.getTax());
        item.setTax(new Tax());
        assertEquals(new Tax(), item.getTax());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(item.getMaterial());
        item.setMaterial(new Material(1L));
        assertEquals(new Material(1L), item.getMaterial());
    }
    
    @Test
    public void testAccessCreatedAt() {
        assertNull(item.getCreatedAt());
        item.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), item.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(item.getLastUpdated());
        item.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), item.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(item.getVersion());
        item.setVersion(1);
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testGetAmount_ReturnsProductOfQuantityAndPrice() {
        item.setQuantity(Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM));
        item.setPrice(Money.parse("CAD 10"));

        assertEquals(Money.parse("CAD 110"), item.getAmount());
    }
}
