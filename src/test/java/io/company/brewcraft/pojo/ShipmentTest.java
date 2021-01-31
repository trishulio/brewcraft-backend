package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentTest {

    private Shipment shipment;

    @BeforeEach
    public void init() {
        shipment = new Shipment();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        shipment = new Shipment(1L, "ABCD-123");

        assertEquals(1L, shipment.getId());
        assertEquals("ABCD-123", shipment.getShipmentNumber());
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
}
