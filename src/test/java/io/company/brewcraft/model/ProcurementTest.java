package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;

public class ProcurementTest {
    private Procurement procurement;

    @BeforeEach
    public void init() {
        procurement = new Procurement();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(procurement.getId());
        assertNull(procurement.getInvoice());
        assertNull(procurement.getShipment());
        assertNull(procurement.getProcurementItems());
    }

    @Test
    public void testArgConstructor_Id() {
        procurement = new Procurement(new ProcurementId(1L, 10L));

        assertEquals(new ProcurementId(1L, 10L), procurement.getId());
        assertEquals(new Shipment(1L), procurement.getShipment());
        assertEquals(new Invoice(10L), procurement.getInvoice());
    }

    @Test
    public void testArgConstructor_ShipmentInvoice() {
        procurement = new Procurement(new Shipment(1L), new Invoice(10L));

        assertEquals(new Shipment(1L), procurement.getShipment());
        assertEquals(new Invoice(10L), procurement.getInvoice());
    }

    @Test
    public void testAccessId_ReturnsNull_WhenAllPropertiesAreNull() {
        procurement.setInvoice(null);
        procurement.setShipment(null);

        assertNull(procurement.getId());
    }

    @Test
    public void testSetId_SetsNullIdsOnInvoiceAndShipment_WhenIdIsNull() {
        procurement.setId(null);

        assertEquals(new Invoice(), procurement.getInvoice());
        assertEquals(new Shipment(), procurement.getShipment());
    }

    @Test
    public void testAccessId_ReturnsNonNullId_WhenChildrenAreNotNull() {
        procurement.setShipment(new Shipment(1L));
        procurement.setInvoice(new Invoice(10L));
        assertEquals(new ProcurementId(1L, 10L), procurement.getId());

        procurement.setShipment(new Shipment(1L));
        procurement.setInvoice(null);
        assertEquals(new ProcurementId(1L, null), procurement.getId());

        procurement.setShipment(null);
        procurement.setInvoice(new Invoice(10L));
        assertEquals(new ProcurementId(null, 10L), procurement.getId());

        procurement.setShipment(new Shipment());
        procurement.setInvoice(new Invoice());
        assertEquals(new ProcurementId(), procurement.getId());
    }

    @Test
    public void testSetId_CreatedNewShipmentAndInvoice_WhenTheyAreNull() {
        procurement.setId(new ProcurementId(1L, 10L));

        assertEquals(new Procurement(new ProcurementId(1L, 10L)), procurement);
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
        procurement.setProcurementItems(List.of(
            new ProcurementItem(new ProcurementItemId(1L, 10L))
        ));

        MaterialLot expectedMaterialLot = new MaterialLot(1L);
        expectedMaterialLot.setIndex(0);
        InvoiceItem expectedInvoiceItem = new InvoiceItem(10L);
        expectedInvoiceItem.setIndex(0);

        List<ProcurementItem> expectedProcurementItems = List.of(new ProcurementItem(expectedMaterialLot, expectedInvoiceItem));
        assertEquals(expectedProcurementItems, procurement.getProcurementItems());
    }

    @Test
    public void testAccessProcurementItems_AddsItemsToInvoiceAndShipment() {
        procurement.setInvoice(new Invoice(1L));
        procurement.setShipment(new Shipment(2L));

        procurement.setProcurementItems(List.of(
            new ProcurementItem(new ProcurementItemId(20L, 10L))
        ));

        Invoice expectedInvoice = new Invoice(1L);
        expectedInvoice.addItem(new InvoiceItem(10L));
        Shipment expectedShipment = new Shipment(2L);
        expectedShipment.addLot(new MaterialLot(20L));
        assertEquals(new Procurement(expectedShipment, expectedInvoice), procurement);
    }

    @Test
    public void testSetProcurementItems_SetsNull_WhenItemsAreNull() {
        procurement.setInvoice(new Invoice(1L));
        procurement.setShipment(new Shipment(2L));

        procurement.setProcurementItems(null);

        assertEquals(new ArrayList<>(), procurement.getProcurementItems());
    }

    @Test
    public void testGetProcurementItemsCount() {
        procurement.setProcurementItems(null);
        assertEquals(0, procurement.getProcurementItemsCount());

        procurement.setProcurementItems(List.of());
        assertEquals(0, procurement.getProcurementItemsCount());

        procurement.setProcurementItems(List.of(new ProcurementItem()));
        assertEquals(0, procurement.getProcurementItemsCount()); // it's zero because empty procurementItem means no invoiceItem and lot is being added.

        procurement.setProcurementItems(List.of(new ProcurementItem(new ProcurementItemId(1L, 10L))));
        assertEquals(1, procurement.getProcurementItemsCount());
    }
}