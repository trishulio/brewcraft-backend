package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.service.impl.procurement.ProcurementIdFactory;

public class ProcurementIdFactoryTest {
    private ProcurementIdFactory factory;

    @BeforeEach
    public void init() {
        factory = ProcurementIdFactory.INSTANCE;
    }

    @Test
    public void testBuild_ShipmentInvoicePurchaseOrder_ReturnsNullId_WhenAllArgsAreNull() {
        ProcurementId id = factory.build(null, null);

        assertNull(id);
    }

    @Test
    public void testBuild_ShipmentInvoicePurchaseOrder_ReturnsEmptyId_WhenArgsHaveNullId() {
        ProcurementId id = factory.build(new Shipment(), new Invoice());

        assertEquals(new ProcurementId(), id);
    }

    @Test
    public void testBuild_ShipmentInvoicePurchaseOrder_ReturnsIdWithAllValues_WhenNoArgIsNull() {
        ProcurementId id = factory.build(new Shipment(1L), new Invoice(10L));

        assertEquals(new ProcurementId(1L, 10L), id);
    }

    @Test
    public void testBuild_ShipmentInvoicePurchaseOrder_ReturnsIdWithNullShipmentId_WhenShipmentIsNull() {
        ProcurementId id = factory.build(null, new Invoice(10L));

        assertEquals(new ProcurementId(null, 10L), id);
    }

    @Test
    public void testBuild_ShipmentInvoicePurchaseOrder_ReturnsIdWithInvoice_WhenInvoiceIsNull() {
        ProcurementId id = factory.build(new Shipment(1L), null);

        assertEquals(new ProcurementId(1L, null), id);
    }

    @Test
    public void testBuild_ShipmentInvoicePurchaseOrder_ReturnsIdWithPurchaseOrder_WhenPurchaseOrderIsNull() {
        ProcurementId id = factory.build(new Shipment(1L), new Invoice(10L));

        assertEquals(new ProcurementId(1L, 10L), id);
    }
}
