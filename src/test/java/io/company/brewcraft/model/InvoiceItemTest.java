package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;
import static tec.units.ri.unit.Units.*;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tec.units.ri.quantity.Quantities;

public class InvoiceItemTest {

    private InvoiceItem item;

    @BeforeEach
    public void init() {
        item = new InvoiceItem();
    }

    @Test
    public void testConstructorWithAllArgs_CallsSetForAllArgs() {
        item = new InvoiceItem(12345L, new Invoice(67890L), Quantities.getQuantity(10, KILOGRAM), Money.parse("CAD 10"), "LOT_12345", null, 1);

        assertEquals(12345L, item.getId());
        assertEquals(new Invoice(67890L), item.getInvoice());
        assertEquals(Quantities.getQuantity(10, KILOGRAM), item.getQuantity());
        assertEquals(Money.parse("CAD 10"), item.getPrice());
        assertEquals("LOT_12345", item.getLot());
        assertEquals(1, item.getVersion());
        assertEquals(null, item.getMaterial());
    }

    @Test
    public void testAccessId() {
        assertNull(item.getId());
        item.setId(98765L);
        assertEquals(98765L, item.getId());
    }

    @Test
    @Disabled
    public void testAccessMaterial() {
        fail("Material objects are not yet created");
    }

    @Test
    public void testAccessQuantity() {
        assertNull(item.getQuantity());
        item.setQuantity(Quantities.getQuantity(10, KILOGRAM));
        assertEquals(Quantities.getQuantity(10, KILOGRAM), item.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(item.getPrice());
        item.setPrice(Money.parse("CAD 100"));
        assertEquals(Money.parse("CAD 100"), item.getPrice());
    }

    @Test
    public void testAccessVersion() {
        assertNull(item.getVersion());
        item.setVersion(12345);
        assertEquals(12345, item.getVersion());
    }

    @Test
    public void testAccessLot() {
        assertNull(item.getLot());
        item.setLot("123456789");
        assertEquals("123456789", item.getLot());
    }

    @Test
    public void testGetAmount_ReturnsProductOfPriceAndQuantity() {
        assertNull(item.getAmount());

        item.setQuantity(Quantities.getQuantity(10, KILOGRAM));
        item.setPrice(Money.parse("CAD 100"));

        assertEquals(Money.parse("CAD 1000"), item.getAmount());

        item.setQuantity(Quantities.getQuantity(5, KILOGRAM));
        item.setPrice(Money.parse("CAD 500"));

        assertEquals(Money.parse("CAD 2500"), item.getAmount());
    }
}
