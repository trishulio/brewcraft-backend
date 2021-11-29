package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;

public class ProcurementTest {
    private Procurement procurement;

    @BeforeEach
    public void init() {
        procurement = new Procurement();
    }

    @Test
    public void testNoArgConstructor() {
        assertEquals(new ProcurementId(), procurement.getId());
        assertEquals(new Invoice(), procurement.getInvoice());
        assertEquals(new Shipment(), procurement.getShipment());
        assertEquals(new PurchaseOrder(), procurement.getPurchaseOrder());
        assertNull(procurement.getProcurementItems());
    }

    @Test
    public void testIdArgConstructor() {
        procurement = new Procurement(new ProcurementId(1L, 10L, 100L));

        assertEquals(new ProcurementId(1L, 10L, 100L), procurement.getId());
        assertEquals(1L, procurement.getShipment().getId());
        assertEquals(10L, procurement.getInvoice().getId());
        assertEquals(100L, procurement.getPurchaseOrder().getId());
    }

    @Test
    public void testShipmentArgConstructor() {
        procurement = new Procurement(new Shipment(1L), new Invoice(10L), new PurchaseOrder(100L), List.of(new ProcurementItem()));

        Shipment shipment = new Shipment(1L);
        shipment.setLots(List.of());
        Invoice invoice = new Invoice(10L);
        invoice.setInvoiceItems(List.of());

        assertEquals(shipment, procurement.getShipment());
        assertEquals(invoice, procurement.getInvoice());
        assertEquals(new PurchaseOrder(100L), procurement.getPurchaseOrder());
        assertEquals(List.of(new ProcurementItem()), procurement.getProcurementItems());
    }

    @Test
    public void testAccessId_ReturnsNull_WhenAllPropertiesAreNull() {
        procurement.setPurchaseOrder(null);
        procurement.setInvoice(null);
        procurement.setShipment(null);

        assertEquals(new ProcurementId(), procurement.getId());
    }

    @Test
    public void testAccessId_ReturnsEmptyId_WhenNonNullChildrenWhenNullIds() {
        procurement.setShipment(new Shipment(1L));
        procurement.setInvoice(new Invoice(10L));
        procurement.setPurchaseOrder(new PurchaseOrder(100L));

        assertEquals(new ProcurementId(1L, 10L, 100L), procurement.getId());
    }

    @Test
    public void testAccessId_ReturnsNonEmptyId_WhenChildrenHaveNonNullId() {
        procurement.setShipment(new Shipment(1L));
        procurement.setInvoice(new Invoice(10L));
        procurement.setPurchaseOrder(new PurchaseOrder(100L));

        assertEquals(new ProcurementId(1L, 10L, 100L), procurement.getId());
    }

    @Test
    public void testAccessPurchaseOrder() {
        procurement.setPurchaseOrder(new PurchaseOrder(100L));

        assertEquals(new PurchaseOrder(100L), procurement.getPurchaseOrder());
    }

    @Test
    public void testAccessInvoice() {
        procurement.setInvoice(new Invoice(10L));

        assertEquals(new Invoice(10L), procurement.getInvoice());
    }

    @Test
    public void testAccessShipment() {
        procurement.setShipment(new Shipment(1L));

        assertEquals(new Shipment(1L), procurement.getShipment());
    }

    @Test
    public void testAccessProcurementItems() {
        procurement.setProcurementItems(List.of(new ProcurementItem()));

        assertEquals(List.of(new ProcurementItem()), procurement.getProcurementItems());
    }

    @Test
    public void testGetProcurementItemsCount() {
        procurement.setProcurementItems(null);
        assertEquals(0, procurement.getProcurementItemsCount());

        procurement.setProcurementItems(List.of());
        assertEquals(0, procurement.getProcurementItemsCount());

        procurement.setProcurementItems(List.of(new ProcurementItem()));
        assertEquals(1, procurement.getProcurementItemsCount());
    }
}