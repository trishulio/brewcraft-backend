package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.service.impl.procurement.ProcurementItemIdFactory;

public class ProcurementItemIdFactoryTest {
    private ProcurementItemIdFactory factory;

    @BeforeEach
    public void init() {
        factory = ProcurementItemIdFactory.INSTANCE;
    }

    @Test
    public void testBuild_InvoiceItemMaterialLotPurchaseOrder_ReturnsNullId_WhenAllArgsAreNull() {
        ProcurementItemId id = factory.build(null, null);

        assertNull(id);
    }

    @Test
    public void testBuild_InvoiceItemMaterialLotPurchaseOrder_ReturnsEmptyId_WhenArgsHaveNullId() {
        ProcurementItemId id = factory.build(new MaterialLot(), new InvoiceItem());

        assertEquals(new ProcurementItemId(), id);
    }

    @Test
    public void testBuild_InvoiceItemMaterialLotPurchaseOrder_ReturnsIdWithAllValues_WhenNoArgIsNull() {
        ProcurementItemId id = factory.build(new MaterialLot(1L), new InvoiceItem(10L));

        assertEquals(new ProcurementItemId(1L, 10L), id);
    }

    @Test
    public void testBuild_InvoiceItemMaterialLotPurchaseOrder_ReturnsIdWithNullInvoiceItemId_WhenInvoiceItemIsNull() {
        ProcurementItemId id = factory.build(new MaterialLot(1L), null);

        assertEquals(new ProcurementItemId(1L, null), id);
    }

    @Test
    public void testBuild_InvoiceItemMaterialLotPurchaseOrder_ReturnsIdWithMaterialLot_WhenMaterialLotIsNull() {
        ProcurementItemId id = factory.build(null, new InvoiceItem(10L));

        assertEquals(new ProcurementItemId(null, 10L), id);
    }

    @Test
    public void testBuild_InvoiceItemMaterialLotPurchaseOrder_ReturnsIdWithPurchaseOrder_WhenPurchaseOrderIsNull() {
        ProcurementItemId id = factory.build(new MaterialLot(1L), new InvoiceItem(10L));

        assertEquals(new ProcurementItemId(1L, 10L), id);
    }
}
