package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOrderTest {
    private PurchaseOrder order;

    @BeforeEach
    public void init() {
        order = new PurchaseOrder();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        order = new PurchaseOrder(1L, "ABCD-123");

        assertEquals(1L, order.getId());
        assertEquals("ABCD-123", order.getOrderNumber());
    }

    @Test
    public void testAccessId() {
        assertNull(order.getId());
        order.setId(1L);
        assertEquals(1L, order.getId());
    }

    @Test
    public void testAccessOrderNumber() {
        assertNull(order.getOrderNumber());
        order.setOrderNumber("ABCD-123");
        assertEquals("ABCD-123", order.getOrderNumber());
    }
}
