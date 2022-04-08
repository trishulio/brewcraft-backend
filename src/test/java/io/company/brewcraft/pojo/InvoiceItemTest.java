package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class InvoiceItemTest {
    private InvoiceItem invoiceItem;

    @BeforeEach
    public void init() {
        invoiceItem = new InvoiceItem();
    }

    @Test
    public void testAllArgsConstructor_SetsValues() {
        InvoiceItem invoiceItem = new InvoiceItem(2L,
                0,
                "desc2",
                Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
                Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
                new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
                new Material(7L),
                LocalDateTime.of(1999, 1, 1, 1, 1),
                LocalDateTime.of(1999, 1, 1, 1, 1),
                1);

        assertEquals(2L, invoiceItem.getId());
        assertEquals("desc2", invoiceItem.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), invoiceItem.getQuantity());
        assertEquals(Money.of(CurrencyUnit.CAD, new BigDecimal("5")), invoiceItem.getPrice());
        assertEquals(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))), invoiceItem.getTax());
        assertEquals(Money.parse("CAD 20"), invoiceItem.getAmount());
        assertEquals(new Material(7L), invoiceItem.getMaterial());
        assertEquals(1, invoiceItem.getVersion());
    }

    @Test
    public void testAccessInvoice() {
        Invoice invoice = new Invoice();

        invoiceItem.setInvoice(invoice);
        assertEquals(invoice, invoiceItem.getInvoice());
    }

    @Test
    public void testAccessId() {
        assertNull(invoiceItem.getId());
        invoiceItem.setId(1L);
        assertEquals(1L, invoiceItem.getId());
    }

    @Test
    public void testAccessDescription() {
        assertNull(invoiceItem.getDescription());
        invoiceItem.setDescription("Descriptio 1");
        assertEquals("Descriptio 1", invoiceItem.getDescription());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(invoiceItem.getQuantity());
        invoiceItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), invoiceItem.getQuantity());
    }

    @Test
    public void testSetQuantity_ThrowsException_WhenMaterialHasIncompatibleUnit() {
        Material material = new Material();
        material.setBaseQuantityUnit(Units.LITRE);
        invoiceItem.setMaterial(material);

        assertThrows(IncompatibleQuantityUnitException.class, () -> invoiceItem.setQuantity(Quantities.getQuantity("10 kg")));
    }

    @Test
    public void testAccessPrice() {
        assertNull(invoiceItem.getPrice());
        invoiceItem.setPrice(Money.parse("CAD 1"));
        assertEquals(Money.parse("CAD 1"), invoiceItem.getPrice());
    }

    @Test
    public void testAccessTax() {
        assertNull(invoiceItem.getTax());
        invoiceItem.setTax(new Tax());
        assertEquals(new Tax(), invoiceItem.getTax());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(invoiceItem.getMaterial());
        invoiceItem.setMaterial(new Material(1L));
        assertEquals(new Material(1L), invoiceItem.getMaterial());
    }

    @Test
    public void testSetMaterial_ThrowsException_WhenMaterialUnitIsIncompatibleWithQuantity() {
        invoiceItem.setQuantity(Quantities.getQuantity("10 kg"));

        Material material = new Material();
        material.setBaseQuantityUnit(Units.LITRE);
        assertThrows(IncompatibleQuantityUnitException.class, () -> invoiceItem.setMaterial(material));
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(invoiceItem.getCreatedAt());
        invoiceItem.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), invoiceItem.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(invoiceItem.getLastUpdated());
        invoiceItem.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), invoiceItem.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoiceItem.getVersion());
        invoiceItem.setVersion(1);
        assertEquals(1, invoiceItem.getVersion());
    }

    @Test
    public void testGetAmount_ReturnsProductOfQuantityAndPrice() {
        invoiceItem.setQuantity(Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM));
        invoiceItem.setPrice(Money.parse("CAD 10"));

        assertEquals(Money.parse("CAD 110"), invoiceItem.getAmount());
    }
}
