package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOrderEntityTest {
    private PurchaseOrderEntity purchaseOrder;

    @BeforeEach
    public void init() {
        purchaseOrder = new PurchaseOrderEntity();
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        purchaseOrder = new PurchaseOrderEntity(1L);
        assertEquals(1L, purchaseOrder.getId());
    }

    @Test
    public void testAllArgConstructor() {
        purchaseOrder = new PurchaseOrderEntity(1L, "ABCDE-12345", new SupplierEntity());

        assertEquals(1L, purchaseOrder.getId());
        assertEquals("ABCDE-12345", purchaseOrder.getOrderNumber());
        assertEquals(new SupplierEntity(), purchaseOrder.getSupplier());
    }

    @Test
    public void testAccessId() {
        assertNull(purchaseOrder.getId());
        purchaseOrder.setId(1L);
        assertEquals(1L, purchaseOrder.getId());
    }

    @Test
    public void testAccessOrderNumber() {
        assertNull(purchaseOrder.getOrderNumber());
        purchaseOrder.setOrderNumber("ABCDE-12345");
        assertEquals("ABCDE-12345", purchaseOrder.getOrderNumber());
    }

    @Test
    public void testAccessSupplier() {
        assertNull(purchaseOrder.getSupplier());
        purchaseOrder.setSupplier(new SupplierEntity());
        assertEquals(new SupplierEntity(), purchaseOrder.getSupplier());
    }
}
