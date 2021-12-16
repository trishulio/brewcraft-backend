package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class MaterialLotTest {
    private MaterialLot lot;

    @BeforeEach
    public void init() {
        this.lot = new MaterialLot();
    }

    @Test
    public void testIdArgConstructor() {
        this.lot = new MaterialLot(100L);
        assertEquals(100L, this.lot.getId());
    }

    @Test
    public void testAllArgConstructor() {
        this.lot = new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), new InvoiceItem(200L), new Storage(300L), LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        assertEquals(1L, this.lot.getId());
        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), this.lot.getQuantity());
        assertEquals(new InvoiceItem(200L), this.lot.getInvoiceItem());
        assertEquals(new Storage(300L), this.lot.getStorage());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), this.lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), this.lot.getLastUpdated());
        assertEquals(1, this.lot.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(this.lot.getId());
        this.lot.setId(1L);
        assertEquals(1L, this.lot.getId());
    }

    @Test
    public void testAccessLotNumber() {
        assertNull(this.lot.getLotNumber());
        this.lot.setLotNumber("LOT");
        assertEquals("LOT", this.lot.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(this.lot.getQuantity());
        this.lot.setQuantity(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testSetQuantity_ThrowsException_WhenQuantityUnitIsIncompatible() {
        Material material = new Material();
        material.setBaseQuantityUnit(Units.LITRE);
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setMaterial(material);

        lot.setInvoiceItem(invoiceItem);
        assertThrows(IncompatibleQuantityUnitException.class, () -> lot.setQuantity(Quantities.getQuantity("10 kg")));
    }

    @Test
    public void testSetQuantity_DoesNotThrowException_WhenMaterialIsNull() {
        InvoiceItem invoiceItem = new InvoiceItem();
        lot.setInvoiceItem(invoiceItem);

        lot.setQuantity(Quantities.getQuantity("10 kg"));
        assertEquals(Quantities.getQuantity("10 kg"), lot.getQuantity());
    }

    @Test
    public void testAccessQuantityOverloadedEntity() {
        assertNull(this.lot.getQuantity());
        this.lot.setQuantity(new QuantityEntity(new UnitEntity("kg"), new BigDecimal("10.00")));
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testAccessShipment() {
        final Shipment shipment = new Shipment();

        this.lot.setShipment(shipment);
        assertEquals(shipment, this.lot.getShipment());
    }

    @Test
    public void testAccessInvoiceItem() {
        assertNull(this.lot.getInvoiceItem());
        this.lot.setInvoiceItem(new InvoiceItem(1L));
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
    }

    @Test
    public void testSetInvoiceItem_ThrowsException_WhenMaterialUnitIsIncompatibleWithQuantity() {
        Material material = new Material();
        material.setBaseQuantityUnit(Units.LITRE);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setMaterial(material);

        lot.setQuantity(Quantities.getQuantity("10 kg"));
        assertThrows(IncompatibleQuantityUnitException.class, () -> lot.setInvoiceItem(invoiceItem));
    }

    @Test
    public void testSetInvoiceItem_DoesNotThrowException_WhenMaterialUnitIsIncompatibleWithQuantity() {
        InvoiceItem invoiceItem = new InvoiceItem();

        lot.setQuantity(Quantities.getQuantity("10 kg"));

        lot.setInvoiceItem(invoiceItem);

        assertEquals(invoiceItem, lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity("10 kg"), lot.getQuantity());
    }

    @Test
    public void testAccessStorage() {
        assertNull(this.lot.getStorage());
        this.lot.setStorage(new Storage(1L));
        assertEquals(new Storage(1L), this.lot.getStorage());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(this.lot.getCreatedAt());
        this.lot.setCreatedAt(LocalDateTime.of(1000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(1000, 1, 1, 0, 0), this.lot.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(this.lot.getLastUpdated());
        this.lot.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), this.lot.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(this.lot.getVersion());
        this.lot.setVersion(99);
        assertEquals(99, this.lot.getVersion());
    }

    @Test
    public void testGetMaterial_ReturnsNull_WhenInvoiceItemIsNull() {
        this.lot.setInvoiceItem(null);

        assertNull(this.lot.getMaterial());
    }

    @Test
    public void testGetMaterial_ReturnsNull_WhenInvoiceItemHasNullMaterial() {
        this.lot.setInvoiceItem(new InvoiceItem(1L));

        assertNull(this.lot.getMaterial());
    }

    @Test
    public void testGetMaterial_ReturnsMaterial_WhenInvoiceItemHasNonNullMaterial() {
        InvoiceItem invoiceItem = new InvoiceItem(1L);
        invoiceItem.setMaterial(new Material(1L));
        this.lot.setInvoiceItem(invoiceItem);

        assertEquals(new Material(1L), this.lot.getMaterial());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        this.lot = new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), new InvoiceItem(200L), new Storage(300L), LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        final String json = "{\"id\":1,\"lotNumber\":\"LOT_1\",\"quantity\":{\"symbol\":\"kg\",\"value\":10},\"invoiceItem\":{\"id\":200,\"description\":null,\"quantity\":null,\"price\":null,\"tax\":null,\"material\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"storage\":{\"id\":300,\"facility\":null,\"name\":null,\"type\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"createdAt\":\"1999-01-01T00:00:00\",\"lastUpdated\":\"2000-01-01T00:00:00\",\"version\":1}";
        JSONAssert.assertEquals(json, this.lot.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}