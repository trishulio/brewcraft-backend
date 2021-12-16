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
        this.shipment = new Shipment();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        final List<MaterialLot> lots = List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        this.shipment = new Shipment(1L,
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

        assertEquals(1L, this.shipment.getId());
        assertEquals("SHIPMENT_1", this.shipment.getShipmentNumber());
        assertEquals("DESCRIPTION_1", this.shipment.getDescription());
        assertEquals(new ShipmentStatus(99L), this.shipment.getShipmentStatus());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), this.shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), this.shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), this.shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), this.shipment.getLastUpdated());
        assertEquals(1, this.shipment.getLots().size());
        assertEquals(1, this.shipment.getVersion());

        final MaterialLot lot = this.shipment.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(this.shipment, lot.getShipment());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(1, lot.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(this.shipment.getId());
        this.shipment.setId(1L);
        assertEquals(1L, this.shipment.getId());
    }

    @Test
    public void testAccessShipmentNumber() {
        assertNull(this.shipment.getShipmentNumber());
        this.shipment.setShipmentNumber("ABCD-123");
        assertEquals("ABCD-123", this.shipment.getShipmentNumber());
    }

    @Test
    public void testAccessStatus() {
        assertNull(this.shipment.getShipmentStatus());
        this.shipment.setShipmentStatus(new ShipmentStatus(99L));
        assertEquals(new ShipmentStatus(99L), this.shipment.getShipmentStatus());
    }

    @Test
    public void testAccessDeliveryDueDate() {
        assertNull(this.shipment.getDeliveryDueDate());
        this.shipment.setDeliveryDueDate(LocalDateTime.of(1998, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1998, 1, 1, 1, 1, 1), this.shipment.getDeliveryDueDate());
    }

    @Test
    public void testAccessDeliveredDate() {
        assertNull(this.shipment.getDeliveredDate());
        this.shipment.setDeliveredDate(LocalDateTime.of(1997, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1997, 1, 1, 1, 1, 1), this.shipment.getDeliveredDate());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(this.shipment.getCreatedAt());
        this.shipment.setCreatedAt(LocalDateTime.of(1996, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1996, 1, 1, 1, 1, 1), this.shipment.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(this.shipment.getLastUpdated());
        this.shipment.setLastUpdated(LocalDateTime.of(1995, 1, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1, 1), this.shipment.getLastUpdated());
    }

    @Test
    public void testAccessLots() {
        assertEquals(List.of(), this.shipment.getLots());
        this.shipment.setLots(List.of(new MaterialLot(1L)));

        final MaterialLot expectedLot = new MaterialLot(1L);
        Shipment expectedShipment = new Shipment();
        expectedShipment.addLot(expectedLot);


        assertEquals(List.of(expectedLot), this.shipment.getLots());
    }

    @Test
    public void testAccessLots_ReturnsNull_WhenSetNull() {
        this.shipment.setLots(null);
        assertEquals(List.of(), this.shipment.getLots());
    }

    @Test
    public void testAccessVersion() {
        assertNull(this.shipment.getVersion());
        this.shipment.setVersion(1);
        assertEquals(1, this.shipment.getVersion());
    }

    @Test
    public void testAddLot_CreatesNewLotList_WhenLotIsNotNull() {
        assertEquals(List.of(), this.shipment.getLots());

        final MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        this.shipment.addLot(lot);

        assertEquals(List.of(lot), this.shipment.getLots());
        assertEquals(this.shipment, lot.getShipment());
    }

    @Test
    public void testAddLot_AddsLotsToList_WhenLotIsNotNull() {
        final MaterialLot existing = new MaterialLot(0L);
        this.shipment.setLots(List.of(existing));
        assertEquals(List.of(existing), this.shipment.getLots());

        final MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        this.shipment.addLot(lot);

        assertEquals(List.of(existing, lot), this.shipment.getLots());
        assertEquals(this.shipment, existing.getShipment());
        assertEquals(this.shipment, lot.getShipment());
    }

    @Test
    public void testAddLot_AddsDuplicates_WhenMultipleAdditionsArePerformed() {
        final MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        this.shipment.addLot(lot);
        this.shipment.addLot(lot);
        this.shipment.addLot(lot);

        assertEquals(List.of(lot, lot, lot), this.shipment.getLots());
        assertEquals(this.shipment, lot.getShipment());
    }

    @Test
    public void testAddLot_DoesNothing_WhenLotIsNull() {
        shipment.addLot(null);
        assertEquals(List.of(), shipment.getLots());
    }

    @Test
    public void testRemoveLot_ReturnsFalse_WhenListIsNull() {
        assertFalse(this.shipment.removeLot(new MaterialLot(1L)));
    }

    @Test
    public void testRemoveLot_ReturnsFalse_WhenListIsEmpty() {
        this.shipment.setLots(new ArrayList<>());
        assertFalse(this.shipment.removeLot(new MaterialLot(1L)));
    }

    @Test
    public void testRemoveLot_ReturnsFalse_WhenLotExistInList() {
        this.shipment.setLots(List.of(new MaterialLot(2L)));

        assertFalse(this.shipment.removeLot(new MaterialLot(1L)));
    }

    @Test
    public void testRemoveLot_ReturnsTrueAndUpdatesLotShipment_WhenLotExist() {
        final MaterialLot lot = new MaterialLot(1L);
        assertNull(lot.getShipment());

        this.shipment.addLot(lot);
        assertEquals(List.of(lot), this.shipment.getLots());
        assertEquals(this.shipment, lot.getShipment());

        assertTrue(this.shipment.removeLot(lot));
        assertNull(lot.getShipment());
    }

    @Test
    public void testGetItemCount_Returns0_WhenLotsIsNull() {
        shipment.setLots(null);

        assertEquals(0, shipment.getLotCount());
    }

    @Test
    public void testGetLotCount_Returns0_WhenLotsIsEmpty() {
        shipment.setLots(List.of());

        assertEquals(0, shipment.getLotCount());
    }

    @Test
    public void testGetLotCount_ReturnsLotCount() {
        shipment.setLots(List.of(new MaterialLot()));

        assertEquals(1, shipment.getLotCount());
    }

    @Test
    public void testSetInvoiceItemsFromInvoice_ThrowsError_WhenInvoiceItemCountDoesNotMeetLotCount() {
        Invoice invoice = new Invoice();
        invoice.addItem(new InvoiceItem(1L));

        assertThrows(IllegalStateException.class, () -> shipment.setInvoiceItemsFromInvoice(invoice));
    }

    @Test
    public void testSetInvoiceItemsFromInvoice_DoesNothing_WhenInvoiceIsNull() {
        shipment.setInvoiceItemsFromInvoice(null);

        assertEquals(new Shipment(), shipment);
    }

    @Test
    public void testSetInvoiceItemsFromInvoice_SetsInvoiceItemOnLots_WhenSameNumberOfItemsArePresent() {
        Invoice invoice = new Invoice();
        invoice.addItem(new InvoiceItem(10L));
        invoice.addItem(new InvoiceItem(20L));

        shipment.addLot(new MaterialLot(1L));
        shipment.addLot(new MaterialLot(2L));

        shipment.setInvoiceItemsFromInvoice(invoice);

        Shipment expected = new Shipment();
        MaterialLot materialLot1 = new MaterialLot(1L);
        MaterialLot materialLot2 = new MaterialLot(2L);
        materialLot1.setInvoiceItem(invoice.getInvoiceItems().get(0));
        materialLot2.setInvoiceItem(invoice.getInvoiceItems().get(1));
        expected.setLots(List.of(materialLot1, materialLot2));

        assertEquals(expected, shipment);
    }
}
