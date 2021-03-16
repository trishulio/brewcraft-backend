package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.utils.SupportedUnits;
import tec.units.ri.quantity.Quantities;

public class InvoiceItemTest {

    private InvoiceItem item;

    @BeforeEach
    public void init() {
        item = new InvoiceItem();
    }

    @Test
    public void testAllArgsConstructor_SetsValues() {
        InvoiceItem item = new InvoiceItem(
            2L,
            "desc2",
            Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L, null, null, null, null, null, null, null, null),
            1
        );

        assertEquals(2L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(Money.of(CurrencyUnit.CAD, new BigDecimal("5")), item.getPrice());
        assertEquals(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), item.getTax());
        assertEquals(Money.parse("CAD 20"), item.getAmount());
        assertEquals(new Material(7L, null, null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(1, item.getVersion());
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
        item.setQuantity(Quantities.getQuantity(10, SupportedUnits.KILOGRAM));
        assertEquals(Quantities.getQuantity(10, SupportedUnits.KILOGRAM), item.getQuantity());
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
        item.setMaterial(new Material(1L, null, null, null, null, null, null, null, null));
        assertEquals(new Material(1L, null, null, null, null, null, null, null, null), item.getMaterial());
    }

    @Test
    public void testAccessVersion() {
        assertNull(item.getVersion());
        item.setVersion(1);
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testGetAmount_ReturnsProductOfQuantityAndPrice() {
        item.setQuantity(Quantities.getQuantity(11, SupportedUnits.KILOGRAM));
        item.setPrice(Money.parse("CAD 10"));

        assertEquals(Money.parse("CAD 110"), item.getAmount());
    }
}
