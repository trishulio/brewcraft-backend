package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class StockLotTest {

    private Lot lot;

    @Test
    public void testLotQuantityConstructor() {
        this.lot = new StockLot("LOT_1", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testShipmentQuantityConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new StockLot(shipment, new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(shipment, this.lot.getShipment());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testMaterialQuantityConstructo() {
        this.lot = new StockLot(new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testStorageQuantityConstructor() {
        this.lot = new StockLot(new Storage(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotMaterialQtyConstructor() {
        this.lot = new StockLot("LOT_1", new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testShipmentMaterialQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new StockLot(shipment, new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testStorageMaterialQtyConstructor() {
        this.lot = new StockLot(new Storage(1L), new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotShipmentMaterialQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new StockLot("LOT_1", shipment, new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotShipmentStorageMaterialQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new StockLot("LOT_1", shipment, new Storage(1L), new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }
}
