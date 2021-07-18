package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ShipmentTest {

    private Shipment shipment;

    @BeforeEach
    public void init() {
        shipment = new Shipment();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        List<MaterialLot> lots = List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        shipment = new Shipment(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            lots,
            1
        );

        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus(99L), shipment.getStatus());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getLots().size());
        assertEquals(1, shipment.getVersion());

        MaterialLot lot = shipment.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(1, lot.getVersion());
    }

    @Test
    public void testInvoiceConstructor() {
        InvoiceItem item = new InvoiceItem();
        item.setQuantity(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM));
        item.setMaterial(new Material(1L));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("ORDER_1");
        invoice.setItems(List.of(item));

        shipment = new Shipment(invoice);

        MaterialLot expectedLot = new MaterialLot();
        expectedLot.setQuantity(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM));
        expectedLot.setMaterial(new Material(1L));
        expectedLot.setInvoiceItem(item);
        expectedLot.setShipment(shipment);

        assertEquals("ORDER_1", invoice.getInvoiceNumber());
        assertEquals(List.of(expectedLot), shipment.getLots());
    }

    @Test
    public void testAccessId() {
        assertNull(shipment.getId());
        shipment.setId(1L);
        assertEquals(1L, shipment.getId());
    }

    @Test
    public void testAccessShipmentNumber() {
        assertNull(shipment.getShipmentNumber());
        shipment.setShipmentNumber("ABCD-123");
        assertEquals("ABCD-123", shipment.getShipmentNumber());
    }

    @Test
    public void testAccessStatus() {
        assertNull(shipment.getStatus());
        shipment.setStatus(new ShipmentStatus(99L));
        assertEquals(new ShipmentStatus(99L), shipment.getStatus());
    }

    @Test
    public void testAccessDeliveryDueDate() {
        assertNull(shipment.getDeliveryDueDate());
        shipment.setDeliveryDueDate(LocalDateTime.of(1998, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1998, 1, 1, 1, 1, 1), shipment.getDeliveryDueDate());
    }

    @Test
    public void testAccessDeliveredDate() {
        assertNull(shipment.getDeliveredDate());
        shipment.setDeliveredDate(LocalDateTime.of(1997, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1997, 1, 1, 1, 1, 1), shipment.getDeliveredDate());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(shipment.getCreatedAt());
        shipment.setCreatedAt(LocalDateTime.of(1996, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1996, 1, 1, 1, 1, 1), shipment.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(shipment.getLastUpdated());
        shipment.setLastUpdated(LocalDateTime.of(1995, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1, 1), shipment.getLastUpdated());
    }

    @Test
    public void testAccessLots() {
        assertNull(shipment.getLots());
        shipment.setLots(List.of(new MaterialLot(1L)));

        MaterialLot expected = new MaterialLot(1L);
        expected.setShipment(new Shipment());
        assertEquals(List.of(expected), shipment.getLots());
    }

    @Test
    public void testAccessVersion() {
        assertNull(shipment.getVersion());
        shipment.setVersion(1);
        assertEquals(1, shipment.getVersion());
    }
    

    @Test
    public void testAddLot_CreatesNewLotList_WhenLotIsNotNull() {
        assertNull(shipment.getLots());

        MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        shipment.addLot(lot);

        assertEquals(List.of(lot), shipment.getLots());
        assertEquals(shipment, lot.getShipment());
    }

    @Test
    public void testAddLot_AddsLotsToList_WhenLotIsNotNull() {
        MaterialLot existing = new MaterialLot(0L);
        shipment.setLots(List.of(existing));
        assertEquals(List.of(existing), shipment.getLots());

        MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        shipment.addLot(lot);

        assertEquals(List.of(existing, lot), shipment.getLots());
        assertEquals(shipment, existing.getShipment());
        assertEquals(shipment, lot.getShipment());
    }

    @Test
    public void testAddLot_AddsLotOnlyOnce_WhenMultipleAdditionsArePerformed() {
        MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        shipment.addLot(lot);
        shipment.addLot(lot);
        shipment.addLot(lot);

        assertEquals(List.of(lot), shipment.getLots());
        assertEquals(shipment, lot.getShipment());
    }

    @Test
    public void testRemoveLot_ReturnsFalse_WhenListIsNull() {
        assertFalse(shipment.removeLot(new MaterialLot(1L)));
    }

    @Test
    public void testRemoveLot_ReturnsFalse_WhenListIsEmpty() {
        shipment.setLots(new ArrayList<>());
        assertFalse(shipment.removeLot(new MaterialLot(1L)));
    }

    @Test
    public void testRemoveLot_ReturnsFalse_WhenLotExistInList() {
        shipment.setLots(List.of(new MaterialLot(2L)));

        assertFalse(shipment.removeLot(new MaterialLot(1L)));
    }

    @Test
    public void testRemoveLot_ReturnsTrueAndUpdatesLotShipment_WhenLotExist() {
        MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        shipment.addLot(lot);
        assertEquals(List.of(lot), shipment.getLots());
        assertEquals(shipment, lot.getShipment());

        assertTrue(shipment.removeLot(lot));
        assertNull(lot.getShipment());
    }
}
