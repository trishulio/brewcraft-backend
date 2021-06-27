package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.Procurement;

public class ProcurementTest {

    private Procurement procurement;

    @BeforeEach
    public void init() {
        procurement = new Procurement();
    }

    @Test
    public void testAllArgConstructor() {
        procurement = new Procurement(new PurchaseOrder(1L), new Invoice(2L), new Shipment(3L));

        assertEquals(new PurchaseOrder(1L), procurement.getPurchaseOrder());
        assertEquals(new Invoice(2L), procurement.getInvoice());
        assertEquals(new Shipment(3L), procurement.getShipment());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(procurement.getPurchaseOrder());

        procurement.setPurchaseOrder(new PurchaseOrder(1L));
        assertEquals(new PurchaseOrder(1L), procurement.getPurchaseOrder());
    }

    @Test
    public void testAccessInvoice() {
        assertNull(procurement.getInvoice());

        procurement.setInvoice(new Invoice(1L));
        assertEquals(new Invoice(1L), procurement.getInvoice());
    }

    @Test
    public void testAccessShipment() {
        assertNull(procurement.getShipment());

        procurement.setShipment(new Shipment(1L));
        assertEquals(new Shipment(1L), procurement.getShipment());
    }
}