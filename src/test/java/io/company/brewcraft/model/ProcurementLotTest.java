package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class ProcurementLotTest {

    private Lot lot;

    @BeforeEach
    public void init() {
        this.lot = new ProcurementLot();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(this.lot.getId());
        assertNull(this.lot.getInvoiceItem());
        assertNull(this.lot.getLotNumber());
        assertNull(this.lot.getMaterial());
        assertNull(this.lot.getQuantity());
        assertNull(this.lot.getShipment());
        assertNull(this.lot.getStorage());
    }

    @Test
    public void testAllArgConstructor() {
        lot = new ProcurementLot(1L, "LOT_NUMBER", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), new Material(1L), new Shipment(2L), new InvoiceItem(3L), new Storage(4L));
        assertEquals(1L, this.lot.getId());
        assertEquals("LOT_NUMBER", this.lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new Shipment(2L), this.lot.getShipment());
        assertEquals(new InvoiceItem(3L), this.lot.getInvoiceItem());
        assertEquals(new Storage(4L), this.lot.getStorage());
    }

    @Test
    public void testAllArgJpaConstructor() {
        lot = new ProcurementLot(1L, "LOT_NUMBER", new Material(1L), "MATERIAL_NAME", new InvoiceItem(3L), new Shipment(2L), new Storage(4L), new UnitEntity("kg"), new BigDecimal("10"));
        assertEquals(1L, this.lot.getId());
        assertEquals("LOT_NUMBER", this.lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new Shipment(2L), this.lot.getShipment());
        assertEquals(new InvoiceItem(3L), this.lot.getInvoiceItem());
        assertEquals(new Storage(4L), this.lot.getStorage());
    }

    @Test
    public void testLotQuantityConstructor() {
        this.lot = new ProcurementLot("LOT_1", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotInvoiceItemQuantityConstructor() {
        this.lot = new ProcurementLot("LOT_1", new InvoiceItem(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testShipmentQuantityConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot(shipment, new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(shipment, this.lot.getShipment());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testShipmentInvoiceItemQuantityConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot(shipment, new InvoiceItem(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testMaterialQuantityConstructo() {
        this.lot = new ProcurementLot(new Material(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testMaterialInvoiceItemQuantityConstructo() {
        this.lot = new ProcurementLot(new Material(1L), new InvoiceItem(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testStorageQuantityConstructor() {
        this.lot = new ProcurementLot(new Storage(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testStorageInvoiceItemQuantityConstructor() {
        this.lot = new ProcurementLot(new Storage(1L), new InvoiceItem(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testInvoiceItemQuantityConstructor() {
        this.lot = new ProcurementLot(new InvoiceItem(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotMaterialQtyConstructor() {
        this.lot = new ProcurementLot("LOT_1", new Material(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotMaterialInvoiceItemQtyConstructor() {
        this.lot = new ProcurementLot("LOT_1", new Material(1L), new InvoiceItem(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testShipmentMaterialQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot(shipment, new Material(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testShipmentMaterialInvoiceItemQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot(shipment, new Material(1L), new InvoiceItem(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testStorageMaterialQtyConstructor() {
        this.lot = new ProcurementLot(new Storage(1L), new Material(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testStorageMaterialInvoiceItemQtyConstructor() {
        this.lot = new ProcurementLot(new Storage(1L), new Material(1L), new InvoiceItem(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotShipmentMaterialQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot("LOT_1", shipment, new Material(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotShipmentMaterialInvoiceItemQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot("LOT_1", shipment, new Material(1L), new InvoiceItem(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Material(1L), this.lot.getMaterial());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotShipmentStorageMaterialQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot("LOT_1", shipment, new Storage(1L), new Material(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
    }

    @Test
    public void testLotShipmentStorageMaterialInvoiceItemQtyConstructor() {
        final Shipment shipment = new Shipment(1L);
        this.lot = new ProcurementLot("LOT_1", shipment, new Storage(1L), new Material(1L), new InvoiceItem(1L), "UNUSED_MATERIAL_NAME", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));

        assertEquals("LOT_1", this.lot.getLotNumber());
        assertEquals(shipment, this.lot.getShipment());
        assertEquals(new Storage(1L), this.lot.getStorage());
        assertEquals(new InvoiceItem(1L), this.lot.getInvoiceItem());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), this.lot.getQuantity());
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
    public void testAccessStorage() {
        assertNull(this.lot.getStorage());
        this.lot.setStorage(new Storage(1L));
        assertEquals(new Storage(1L), this.lot.getStorage());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        this.lot = new ProcurementLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), new Material(3L), new Shipment(1L), new InvoiceItem(4L), new Storage(2L));

        final String json = "{\"id\":1,\"lotNumber\":\"LOT_1\",\"quantity\":{\"symbol\":\"kg\",\"value\":10},\"material\":{\"id\":3,\"name\":null,\"description\":null,\"category\":null,\"upc\":null,\"baseQuantityUnit\":null,\"imageSrc\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"shipment\":{\"id\":1,\"shipmentNumber\":null,\"description\":null,\"shipmentStatus\":null,\"deliveryDueDate\":null,\"deliveredDate\":null,\"lots\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"invoiceItem\":{\"id\":4,\"description\":null,\"quantity\":null,\"price\":null,\"tax\":null,\"material\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"storage\":{\"id\":2,\"facility\":null,\"name\":null,\"type\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}}";
        JSONAssert.assertEquals(json, this.lot.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
