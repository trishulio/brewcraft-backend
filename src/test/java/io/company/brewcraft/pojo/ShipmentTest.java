package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.ShipmentStatus;
import tec.uom.se.quantity.Quantities;
import io.company.brewcraft.utils.SupportedUnits;

public class ShipmentTest {

    private Shipment shipment;

    @BeforeEach
    public void init() {
        shipment = new Shipment();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        List<ShipmentItem> items = List.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        shipment = new Shipment(1L,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            items,
            1
        );

        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(null, shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getItems().size());
        assertEquals(1, shipment.getVersion());

        ShipmentItem item = shipment.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(shipment, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(1, item.getVersion());
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
    public void testAccessLotNumber() {
        assertNull(shipment.getLotNumber());
        shipment.setLotNumber("ABCD-123");
        assertEquals("ABCD-123", shipment.getLotNumber());
    }

    @Test
    public void testAccessStatus() {
        assertNull(shipment.getStatus());
        shipment.setStatus(new ShipmentStatus("RECEIVED"));
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
    }

    @Test
    public void testAccessInvoice() {
        assertNull(shipment.getInvoice());
        shipment.setInvoice(new Invoice(1L));
        assertEquals(new Invoice(1L), shipment.getInvoice());
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
    public void testAccessItems() {
        assertNull(shipment.getItems());
        shipment.setItems(List.of(new ShipmentItem(1L)));

        ShipmentItem expected = new ShipmentItem(1L);
        expected.setShipment(shipment);
        assertEquals(List.of(expected), shipment.getItems());
    }

    @Test
    public void testAccessVersion() {
        assertNull(shipment.getVersion());
        shipment.setVersion(1);
        assertEquals(1, shipment.getVersion());
    }
}
