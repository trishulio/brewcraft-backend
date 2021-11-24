package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;

public class ProcurementItemTest {
    private ProcurementItem procurementItem;

    @BeforeEach
    public void init() {
        procurementItem = new ProcurementItem();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(procurementItem.getId());
        assertNull(procurementItem.getInvoiceItem());
        assertNull(procurementItem.getMaterialLot());
    }

    @Test
    public void testAllArgConstructor() {
        procurementItem = new ProcurementItem(new MaterialLot(1L), new InvoiceItem(10L));
        assertEquals(new ProcurementItemId(1L, 10L), procurementItem.getId());
        assertEquals(new MaterialLot(1L), procurementItem.getMaterialLot());
        assertEquals(new InvoiceItem(10L), procurementItem.getInvoiceItem());
    }

    @Test
    public void testIdArgConstructor() {
        procurementItem = new ProcurementItem(new ProcurementItemId(1L, 10L));
        assertEquals(new ProcurementItemId(1L, 10L), procurementItem.getId());
        assertEquals(new MaterialLot(1L), procurementItem.getMaterialLot());
        assertEquals(new InvoiceItem(10L), procurementItem.getInvoiceItem());
    }

    @Test
    public void testAccessId_ReturnsNonNullKey_WhenMaterialLotAndInvoiceItemHaveNotNullKey() {
        procurementItem.setMaterialLot(new MaterialLot(1L));
        procurementItem.setInvoiceItem(new InvoiceItem(10L));

        assertEquals(new ProcurementItemId(1L, 10L), procurementItem.getId());
    }

    @Test
    public void testAccessId_ReturnsEmptyKey_WhenMaterialLotIdAndInvoiceItemIdAreNull() {
        procurementItem.setMaterialLot(new MaterialLot());
        procurementItem.setInvoiceItem(new InvoiceItem());

        assertEquals(new ProcurementItemId(), procurementItem.getId());
    }

    @Test
    public void testAccessId_ReturnsNull_WhenMaterialLotAndInvoiceItemAreNull() {
        procurementItem.setMaterialLot(null);
        procurementItem.setInvoiceItem(null);

        assertEquals(new ProcurementItemId(), procurementItem.getId());
    }

    @Test
    public void testAccessInvoiceItem() {
        procurementItem.setInvoiceItem(new InvoiceItem(10L));
        assertEquals(new InvoiceItem(10L), procurementItem.getInvoiceItem());
    }

    @Test
    public void testAccessMaterialLot() {
        procurementItem.setMaterialLot(new MaterialLot(1L));
        assertEquals(new MaterialLot(1L), procurementItem.getMaterialLot());
    }
}
