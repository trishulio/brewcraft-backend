package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.service.impl.procurement.ProcurementFactory;
import io.company.brewcraft.service.impl.procurement.ProcurementItemFactory;

public class ProcurementFactoryTest {
    private ProcurementFactory factory;

    private ProcurementItemFactory mProcurementItemFactory;

    @BeforeEach
    public void init() {
        mProcurementItemFactory = mock(ProcurementItemFactory.class);
        factory = new ProcurementFactory(mProcurementItemFactory);
    }

    @Test
    public void testBuildFromShipment_ReturnsNull_WhenShipmentIsNull() {
        assertNull(factory.buildFromShipment(null));
    }

    @Test
    public void testBuildFromShipment_ReturnsProcurementFromShipmentAndChildren_WhenShipmentIsNotNull() {
        Invoice invoice = new Invoice(10L);
        invoice.setPurchaseOrder(new PurchaseOrder(100L));

        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        doReturn(new ProcurementItem()).when(mProcurementItemFactory).buildFromLot(materialLot);

        Procurement procurement = factory.buildFromShipment(shipment);

        Procurement expected = new Procurement(
            new Shipment(1L),
            new Invoice(10L),
            new PurchaseOrder(100L),
            List.of(new ProcurementItem())
        );

        assertEquals(expected, procurement);
    }

    @Test
    public void testBuildFromShipment_ReturnsProcurementWithNullPurchaseOrder_WhenShipmentDoesNotHavePurchaseOrder() {
        Invoice invoice = new Invoice(10L);
        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        doReturn(new ProcurementItem()).when(mProcurementItemFactory).buildFromLot(materialLot);

        Procurement procurement = factory.buildFromShipment(shipment);

        Procurement expected = new Procurement(
            new Shipment(1L),
            new Invoice(10L),
            null,
            List.of(new ProcurementItem())
        );

        assertEquals(expected, procurement);
    }

    @Test
    public void testBuildFromShipment_ReturnsProcurementWithNullInvoice_WhenShipmentDoesNotHaveInvoice() {
        InvoiceItem invoiceItem = new InvoiceItem(20L);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        doReturn(new ProcurementItem()).when(mProcurementItemFactory).buildFromLot(materialLot);

        Procurement procurement = factory.buildFromShipment(shipment);

        Procurement expected = new Procurement(
            new Shipment(1L),
            null,
            null,
            List.of(new ProcurementItem())
        );

        assertEquals(expected, procurement);
    }

    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsTrue_WhenProcurementIdMatchesShipmentChildren() {
        Invoice invoice = new Invoice(10L);
        invoice.setPurchaseOrder(new PurchaseOrder(100L));

        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(1L, 10L, 100L), shipment);
        assertTrue(b);
    }


    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsFalse_WhenProcurementIdDoesNotMatchShipmentId() {
        Invoice invoice = new Invoice(10L);
        invoice.setPurchaseOrder(new PurchaseOrder(100L));

        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(99L, 10L, 100L), shipment);
        assertFalse(b);
    }

    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsFalse_WhenProcurementIdDoesNotMatchInvoiceId() {
        Invoice invoice = new Invoice(10L);
        invoice.setPurchaseOrder(new PurchaseOrder(100L));

        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(1L, 99L, 100L), shipment);
        assertFalse(b);
    }

    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsFalse_WhenProcurementIdDoesNotMatchPurchaseOrderId() {
        Invoice invoice = new Invoice(10L);
        invoice.setPurchaseOrder(new PurchaseOrder(100L));

        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(1L, 10L, 99L), shipment);
        assertFalse(b);
    }

    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsFalse_WhenPurchaseOrderIsNull() {
        Invoice invoice = new Invoice(10L);

        InvoiceItem invoiceItem = new InvoiceItem(20L);
        invoice.addItem(invoiceItem);

        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setInvoiceItem(invoiceItem);

        Shipment shipment = new Shipment(1L);
        shipment.addLot(materialLot);

        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(1L, 10L, 100L), shipment);
        assertFalse(b);
    }

    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsFalse_WhenInvoiceIsNull() {
        Shipment shipment = new Shipment(1L);

        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(1L, 10L, 100L), shipment);
        assertFalse(b);
    }


    @Test
    public void testIsEligibleShipmentForProcurement_ReturnsFalse_WhenShipmentIsNull() {
        boolean b = factory.isEligibleShipmentForProcurement(new ProcurementId(1L, 10L, 100L), null);
        assertFalse(b);
    }

    @Test
    public void testBuildFromShipmentIfIdMatches_ReturnsProcurementFromShipment_WhenEligibleIsTrue() {
        factory = spy(factory);
        doReturn(true).when(factory).isEligibleShipmentForProcurement(new ProcurementId(), new Shipment(1L));
        doReturn(new Procurement()).when(factory).buildFromShipment(new Shipment(1L));

        Procurement procurement = factory.buildFromShipmentIfIdMatches(new ProcurementId(), new Shipment(1L));
        assertEquals(new Procurement(), procurement);
    }

    @Test
    public void testBuildFromShipmentIfIdMatches_ReturnsNull_WhenEligibleIsFalse() {
        factory = spy(factory);
        doReturn(false).when(factory).isEligibleShipmentForProcurement(new ProcurementId(), new Shipment(1L));
        doReturn(new Procurement()).when(factory).buildFromShipment(new Shipment(1L));

        Procurement procurement = factory.buildFromShipmentIfIdMatches(new ProcurementId(), new Shipment(1L));
        assertNull(procurement);
    }
}
