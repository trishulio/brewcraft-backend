package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class MaterialLotTest {
    private MaterialLot lot;

    @BeforeEach
    public void init() {
        lot = new MaterialLot();
    }

    @Test
    public void testIdArgConstructor() {
        lot = new MaterialLot(100L);
        assertEquals(100L, lot.getId());
    }

    @Test
    public void testAllArgConstructor() {
        lot = new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), new Material(100L), new InvoiceItem(200L), new Storage(300L), LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        assertEquals(1L, lot.getId());
        assertEquals("LOT_1", lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), lot.getQuantity());
        assertEquals(new Material(100L), lot.getMaterial());
        assertEquals(new InvoiceItem(200L), lot.getInvoiceItem());
        assertEquals(new Storage(300L), lot.getStorage());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), lot.getLastUpdated());
        assertEquals(1, lot.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(lot.getId());
        lot.setId(1L);
        assertEquals(1L, lot.getId());
    }

    @Test
    public void testAccessLotNumber() {
        assertNull(lot.getLotNumber());
        lot.setLotNumber("LOT");
        assertEquals("LOT", lot.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(lot.getQuantity());
        lot.setQuantity(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), lot.getQuantity());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(lot.getMaterial());
        lot.setMaterial(new Material(1L));
        assertEquals(new Material(1L), lot.getMaterial());
    }

    @Test
    public void testAccessShipment() {
        Shipment shipment = new Shipment();

        lot.setShipment(shipment);
        assertEquals(shipment, lot.getShipment());
    }

    @Test
    public void testSetShipment_RemovesItselfFromTheSourceMaterialLotsAndSetsNewListOnTarget_WhenTargetMaterialLotsAreNull() {
        Shipment source = new Shipment();
        source.setLots(List.of(lot));

        Shipment target = new Shipment();
        lot.setShipment(target);

        assertEquals(List.of(), source.getLots());
        assertEquals(List.of(lot), target.getLots());
    }

    @Test
    public void testSetShipment_RemovesItemFromShipmentAndAddsToExistingTargetMaterialLotList_WhenTargetMaterialLotsAreNotNull() {
        Shipment source = new Shipment();
        source.setLots(List.of(lot));

        Shipment target = new Shipment();
        target.setLots(List.of(new MaterialLot()));

        lot.setShipment(target);

        assertEquals(List.of(), source.getLots());

        Shipment expectedTarget = new Shipment();
        expectedTarget.setLots(List.of(new MaterialLot(), new MaterialLot()));
        assertEquals(expectedTarget, target);
    }

    @Test
    public void testAccessInvoiceItem() {
        assertNull(lot.getInvoiceItem());
        lot.setInvoiceItem(new InvoiceItem(1L));
        assertEquals(new InvoiceItem(1L), lot.getInvoiceItem());
    }

    @Test
    public void testAccessStorage() {
        assertNull(lot.getStorage());
        lot.setStorage(new Storage(1L));
        assertEquals(new Storage(1L), lot.getStorage());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(lot.getCreatedAt());
        lot.setCreatedAt(LocalDateTime.of(1000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(1000, 1, 1, 0, 0), lot.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(lot.getLastUpdated());
        lot.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), lot.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(lot.getVersion());
        lot.setVersion(99);
        assertEquals(99, lot.getVersion());
    }
}