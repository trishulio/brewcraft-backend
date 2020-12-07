package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;
import static tec.units.ri.unit.Units.*;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tec.units.ri.quantity.Quantities;

public class InvoiceItemEntityTest {

    private InvoiceItemEntity item;

    @BeforeEach
    public void init() {
        item = new InvoiceItemEntity();
    }

    @Test
    public void testConstructorWithAllArgs_CallsSetForAllArgs() {
        item = new InvoiceItemEntity(12345L, new InvoiceEntity(67890L), new QuantityEntity(11L), new MoneyEntity(22L), "LOT_12345", new MaterialEntity(33L), 1);

        assertEquals(12345L, item.getId());
        assertEquals(new InvoiceEntity(67890L), item.getInvoice());
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
        assertNull(item.getMaterial());
        item.setMaterial(new MaterialEntity(11L));
        assertEquals(new Material(11L), item.getMaterial());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(item.getQuantity());
        item.setQuantity(new QuantityEntity(111L));
        assertEquals(new QuantityEntity(111L), item.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(item.getPrice());
        item.setPrice(new MoneyEntity(222L));
        assertEquals(new MoneyEntity(222L), item.getPrice());
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

    // TODO: Move to the POJO test when that is created.
//    @Test
//    public void testGetAmount_ReturnsProductOfPriceAndQuantity() {
//        assertNull(item.getPrice());
//
//        item.setQuantity(Quantities.getQuantity(10, KILOGRAM));
//        item.setPrice(Money.parse("CAD 100"));
//
//        assertEquals(Money.parse("CAD 1000"), item.getPrice());
//
//        item.setQuantity(Quantities.getQuantity(5, KILOGRAM));
//        item.setPrice(Money.parse("CAD 500"));
//
//        assertEquals(Money.parse("CAD 2500"), item.getPrice());
//    }
}
