package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;

public class PurchaseOrderTest {
    private PurchaseOrder order;

    @BeforeEach
    public void init() {
        order = new PurchaseOrder();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        order = new PurchaseOrder(1L, "ABCD-123", new Supplier(), 1);

        assertEquals(1L, order.getId());
        assertEquals("ABCD-123", order.getOrderNumber());
        assertEquals(new Supplier(), order.getSupplier());
        assertEquals(1, order.getVersion());
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

    @Test
    public void testAccessSupplier() {
        assertNull(order.getSupplier());
        order.setSupplier(new Supplier());
        assertEquals(new Supplier(), order.getSupplier());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(order.getCreatedAt());
        order.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), order.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(order.getLastUpdated());
        order.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), order.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(order.getVersion());
        order.setVersion(1);
        assertEquals(1, order.getVersion());
    }
}
