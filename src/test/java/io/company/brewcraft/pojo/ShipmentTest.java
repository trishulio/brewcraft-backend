package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ShipmentTest {

    private Shipment shipment;

    @BeforeEach
    public void init() {
        shipment = new Shipment();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        List<MaterialLot> lots = List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new Material(1L), null, new InvoiceItem(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        shipment = new Shipment(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
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
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
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
        shipment.setStatus(new ShipmentStatus("RECEIVED"));
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
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
}
