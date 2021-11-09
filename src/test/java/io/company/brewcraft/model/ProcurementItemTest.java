package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementItemTest {
    private ProcurementItem procurementItem;

    @BeforeEach
    public void init() {
        procurementItem = new ProcurementItem();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(procurementItem.getId());
        assertNull(procurementItem.getDescription());
        assertNull(procurementItem.getLotNumber());
        assertNull(procurementItem.getQuantity());
        assertNull(procurementItem.getStorage());
        assertNull(procurementItem.getPrice());
        assertNull(procurementItem.getTax());
        assertNull(procurementItem.getMaterial());
        assertNull(procurementItem.getCreatedAt());
        assertNull(procurementItem.getLastUpdated());
        assertNull(procurementItem.getInvoiceItemVersion());
        assertNull(procurementItem.getVersion());
    }

    @Test
    public void testAllArgConstructor() {
        procurementItem = new ProcurementItem(
            new ProcurementItemId(1L, 2L),
            "DESCRIPTION",
            "LOT_NUMBER",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            new Storage(1L),
            Money.parse("CAD 10"),
            new Tax(Money.parse("CAD 20")),
            new Material(1L),
            LocalDateTime.of(1999, 1, 1, 0, 0), // createdAt
            LocalDateTime.of(2000, 1, 1, 0, 0), // lastUpdated
            1,
            10
        );

        assertEquals(new ProcurementItemId(1L, 2L), procurementItem.getId());
        assertEquals("DESCRIPTION", procurementItem.getDescription());
        assertEquals("LOT_NUMBER", procurementItem.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), procurementItem.getQuantity());
        assertEquals(new Storage(1L), procurementItem.getStorage());
        assertEquals(Money.parse("CAD 10"), procurementItem.getPrice());
        assertEquals(new Tax(Money.parse("CAD 20")), procurementItem.getTax());
        assertEquals(new Material(1L), procurementItem.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), procurementItem.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurementItem.getLastUpdated());
        assertEquals(1, procurementItem.getInvoiceItemVersion());
        assertEquals(10, procurementItem.getVersion());
    }

    @Test
    public void testIdArgConstructor() {
        procurementItem = new ProcurementItem(new ProcurementItemId(1L, 2L));
        assertEquals(new ProcurementItemId(1L, 2L), procurementItem.getId());
    }

    @Test
    public void testShipmentInvoiceConstructor() {
        MaterialLot lot = new MaterialLot(
            1L,
            "LOT_1",
            Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM),
            null,
            new Storage(1L),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            10
        );
        InvoiceItem invoiceItem = new InvoiceItem(
            2L,
            "DESCRIPTION",
            Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM),
            Money.parse("CAD 10"),
            new Tax(Money.parse("CAD 20")),
            new Material(1L),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        procurementItem = new ProcurementItem(lot, invoiceItem);

        assertEquals(new ProcurementItemId(1L, 2L), procurementItem.getId());
        assertEquals("DESCRIPTION", procurementItem.getDescription());
        assertEquals("LOT_1", procurementItem.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), procurementItem.getQuantity());
        assertEquals(new Storage(1L), procurementItem.getStorage());
        assertEquals(Money.parse("CAD 10"), procurementItem.getPrice());
        assertEquals(new Tax(Money.parse("CAD 20")), procurementItem.getTax());
        assertEquals(new Material(1L), procurementItem.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), procurementItem.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurementItem.getLastUpdated());
        assertEquals(1, procurementItem.getInvoiceItemVersion());
        assertEquals(10, procurementItem.getVersion());
    }

    @Test
    public void testAccessId() {
        procurementItem.setId(new ProcurementItemId(1L, 2L));
        assertEquals(new ProcurementItemId(1L, 2L), procurementItem.getId());
    }

    @Test
    public void testAccessDescription() {
        procurementItem.setDescription("DESCRIPTION_1");
        assertEquals("DESCRIPTION_1", procurementItem.getDescription());
    }

    @Test
    public void testAccessLotNumber() {
        procurementItem.setLotNumber("LOT");
        assertEquals("LOT", procurementItem.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), procurementItem.getQuantity());
    }

    @Test
    public void testAccessStorage() {
        procurementItem.setStorage(new Storage(2L));
        assertEquals(new Storage(2L), procurementItem.getStorage());
    }

    @Test
    public void testAccessPrice() {
        procurementItem.setPrice(Money.parse("CAD 20"));
        assertEquals(Money.parse("CAD 20"), procurementItem.getPrice());
    }

    @Test
    public void testAccessTax() {
        procurementItem.setTax(new Tax(Money.parse("CAD 1")));
        assertEquals(new Tax(Money.parse("CAD 1")), procurementItem.getTax());
    }

    @Test
    public void testAccessMaterial() {
        procurementItem.setMaterial(new Material(1L));
        assertEquals(new Material(1L), procurementItem.getMaterial());
    }

    @Test
    public void testAccessInvoiceItemVersion() {
        procurementItem.setInvoiceItemVersion(1);
        assertEquals(1, procurementItem.getInvoiceItemVersion());
    }

    @Test
    public void testAccessAmount() {
        procurementItem.setPrice(Money.parse("CAD 10"));
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        assertEquals(Money.parse("CAD 100"), procurementItem.getAmount());
    }

    @Test
    public void testAccessCreatedAt() {
        procurementItem.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurementItem.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        procurementItem.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), procurementItem.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        procurementItem.setVersion(10);
        assertEquals(10, procurementItem.getVersion());
    }

    @Test
    public void testAccessProcurement() {
        procurementItem.setProcurement(new Procurement(new ProcurementId(1L, 2L)));
        assertEquals(new Procurement(new ProcurementId(1L, 2L)), procurementItem.getProcurement());
    }

    @Test
    public void testBuildInvoiceItem_ReturnsInvoiceItem() {
        procurementItem.setId(new ProcurementItemId(null, 2L));
        procurementItem.setDescription("DESCRIPTION_ITEM");
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM));
        procurementItem.setPrice(Money.of(CurrencyUnit.CAD, new BigDecimal("5")));
        procurementItem.setTax(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))));
        procurementItem.setMaterial(new Material(7L));
        procurementItem.setCreatedAt(LocalDateTime.of(1999, 1, 1, 1, 1));
        procurementItem.setLastUpdated(LocalDateTime.of(2000, 1, 1, 1, 1));
        procurementItem.setInvoiceItemVersion(1);

        InvoiceItem invoiceItem = procurementItem.buildInvoiceItem();

        InvoiceItem expected = new InvoiceItem(
            2L,
            "DESCRIPTION_ITEM",
            Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            LocalDateTime.of(2000, 1, 1, 1, 1),
            1
        );

        assertEquals(expected, invoiceItem);
    }

    @Test
    public void testBuildMaterialLot_ReturnsMaterialLot() {
        procurementItem.setId(new ProcurementItemId(1L, null));
        procurementItem.setLotNumber("LOT_1");
        procurementItem.setQuantity(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM));
        procurementItem.setStorage(new Storage(300L));
        procurementItem.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        procurementItem.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        procurementItem.setVersion(1);

        MaterialLot lot = procurementItem.buildMaterialLot();

        MaterialLot expected = new MaterialLot(
            1L,
            "LOT_1",
            Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM),
            null,
            new Storage(300L),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        assertEquals(expected, lot);
    }

    @Test
    public void testUpdateProperties_ResetAllProperties_WhenLotIsNull() {
        procurementItem.updateProperties((MaterialLot) null);
        assertNull(procurementItem.getId());
        assertNull(procurementItem.getLotNumber());
        assertNull(procurementItem.getQuantity());
        assertNull(procurementItem.getStorage());
        assertNull(procurementItem.getLastUpdated());
        assertNull(procurementItem.getCreatedAt());
        assertNull(procurementItem.getVersion());
    }

    @Test
    public void testUpdateProperties_SetsValuesFromLot() {
        MaterialLot lot = new MaterialLot(
            1L,
            "LOT_1",
            Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM),
            null,
            new Storage(300L),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            1
        );

        procurementItem.updateProperties(lot);

        ProcurementItem expected = new ProcurementItem();
        expected.setId(new ProcurementItemId(1L, null));
        expected.setLotNumber("LOT_1");
        expected.setQuantity(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM));
        expected.setStorage(new Storage(300L));
        expected.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        expected.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        expected.setVersion(1);
        assertEquals(expected, procurementItem);
    }

    @Test
    public void testUpdateProperties_ResetAllProperties_WhenInvoiceItemIsNull() {
        procurementItem.updateProperties((InvoiceItem) null);
        assertNull(procurementItem.getId());
        assertNull(procurementItem.getDescription());
        assertNull(procurementItem.getQuantity());
        assertNull(procurementItem.getPrice());
        assertNull(procurementItem.getTax());
        assertNull(procurementItem.getMaterial());
        assertNull(procurementItem.getCreatedAt());
        assertNull(procurementItem.getLastUpdated());
        assertNull(procurementItem.getInvoiceItemVersion());
    }

    @Test
    public void testUpdateProperties_SetsValuesFromInvoiceItem() {
        InvoiceItem invoiceItem = new InvoiceItem(
            2L,
            "DESCRIPTION_ITEM",
            Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM),
            Money.of(CurrencyUnit.CAD, new BigDecimal("5")),
            new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))),
            new Material(7L),
            LocalDateTime.of(2000, 1, 1, 1, 1),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            1
        );

        procurementItem.updateProperties(invoiceItem);

        ProcurementItem expected = new ProcurementItem();
        expected.setId(new ProcurementItemId(null, 2L));
        expected.setDescription("DESCRIPTION_ITEM");
        expected.setQuantity(Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM));
        expected.setPrice(Money.of(CurrencyUnit.CAD, new BigDecimal("5")));
        expected.setTax(new Tax(Money.of(CurrencyUnit.CAD, new BigDecimal("6"))));
        expected.setMaterial(new Material(7L));
        expected.setCreatedAt(LocalDateTime.of(2000, 1, 1, 1, 1));
        expected.setLastUpdated(LocalDateTime.of(1999, 1, 1, 1, 1));
        expected.setInvoiceItemVersion(1);

        assertEquals(expected, procurementItem);
    }
}
