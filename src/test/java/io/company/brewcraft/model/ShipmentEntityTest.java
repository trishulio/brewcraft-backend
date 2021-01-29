package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentEntityTest {

    private ShipmentEntity shipment;

    @BeforeEach
    public void init() {
        shipment = new ShipmentEntity();
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        shipment = new ShipmentEntity(1L);
        assertEquals(1L, shipment.getId());
    }

    @Test
    public void testAllArgConstructor() {
        shipment = new ShipmentEntity(1L, "ABCDE-12345", new InvoiceEntity(2L));

        assertEquals(1L, shipment.getId());
        assertEquals("ABCDE-12345", shipment.getShipmentNumber());
        assertEquals(new InvoiceEntity(2L), shipment.getInvoice());
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
        shipment.setShipmentNumber("ABCDE-12345");
        assertEquals("ABCDE-12345", shipment.getShipmentNumber());
    }

    @Test
    public void testAccessInvoice() {
        assertNull(shipment.getInvoice());
        shipment.setInvoice(new InvoiceEntity(2L));
        assertEquals(new InvoiceEntity(2L), shipment.getInvoice());
    }
}
