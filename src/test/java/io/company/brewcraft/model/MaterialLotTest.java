package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
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
    public void testLotQuantityConstructor() {
        lot = new MaterialLot("LOT_1", new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals("LOT_1", lot.getLotNumber());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }
    
    @Test
    public void testShipmentQuantityConstructor() {
        Shipment shipment = new Shipment(1L);
        lot = new MaterialLot(shipment, new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals(shipment, lot.getShipment());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }
    
    @Test
    public void testMaterialQuantityConstructo() {
        lot = new MaterialLot(new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }
    
    @Test
    public void testStorageQuantityConstructor() {
        lot = new MaterialLot(new Storage(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals(new Storage(1L), lot.getStorage());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }

    @Test
    public void testLotMaterialQtyConstructor() {
        lot = new MaterialLot("LOT_1", new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals("LOT_1", lot.getLotNumber());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }

    @Test
    public void testShipmentMaterialQtyConstructor() {
        Shipment shipment = new Shipment(1L);
        lot = new MaterialLot(shipment, new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }

    @Test
    public void testStorageMaterialQtyConstructor() {
        lot = new MaterialLot(new Storage(1L), new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals(new Storage(1L), lot.getStorage());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }
    
    @Test
    public void testLotShipmentMaterialQtyConstructor() {
        Shipment shipment = new Shipment(1L);
        lot = new MaterialLot("LOT_1", shipment, new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals("LOT_1", lot.getLotNumber());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }
    
    @Test
    public void testLotShipmentStorageMaterialQtyConstructor() {
        Shipment shipment = new Shipment(1L);
        lot = new MaterialLot("LOT_1", shipment, new Storage(1L), new Material(1L), new UnitEntity("kg", "Kilogram"), new BigDecimal("10"));
        
        assertEquals("LOT_1", lot.getLotNumber());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Storage(1L), lot.getStorage());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), Units.KILOGRAM), lot.getQuantity());
    }

    public void testInvoiceItemConstructor() {
        InvoiceItem item = new InvoiceItem();
        item.setQuantity(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM));
        item.setMaterial(new Material(1L));

        lot = new MaterialLot(item);

        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(item, lot.getInvoiceItem());
        assertEquals(new Material(1L), lot.getMaterial());
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
    public void testAccessQuantityOverloadedEntity() {
        assertNull(lot.getQuantity());
        lot.setQuantity(new QuantityEntity(new UnitEntity("kg"), new BigDecimal("10.00")));
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

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        lot = new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), new Material(100L), new InvoiceItem(200L), new Storage(300L), LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        final String json = "{\"id\":1,\"lotNumber\":\"LOT_1\",\"quantity\":{\"symbol\":\"kg\",\"value\":10},\"material\":{\"id\":100,\"name\":null,\"description\":null,\"category\":null,\"upc\":null,\"baseQuantityUnit\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"invoiceItem\":{\"id\":200,\"description\":null,\"quantity\":null,\"price\":null,\"tax\":null,\"material\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null,\"amount\":null},\"storage\":{\"id\":300,\"facility\":null,\"name\":null,\"type\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"createdAt\":{\"nano\":0,\"year\":1999,\"monthValue\":1,\"dayOfMonth\":1,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":1,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2000,\"monthValue\":1,\"dayOfMonth\":1,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"SATURDAY\",\"dayOfYear\":1,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        JSONAssert.assertEquals(json, lot.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}