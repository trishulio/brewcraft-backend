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
        this.order = new PurchaseOrder();
    }

    @Test
    public void testAllArgsConstructor_SetsAllValues() {
        this.order = new PurchaseOrder(1L, "ABCD-123", new Supplier(), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1);

        assertEquals(1L, this.order.getId());
        assertEquals("ABCD-123", this.order.getOrderNumber());
        assertEquals(new Supplier(), this.order.getSupplier());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), this.order.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), this.order.getLastUpdated());
        assertEquals(1, this.order.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(this.order.getId());
        this.order.setId(1L);
        assertEquals(1L, this.order.getId());
    }

    @Test
    public void testAccessOrderNumber() {
        assertNull(this.order.getOrderNumber());
        this.order.setOrderNumber("ABCD-123");
        assertEquals("ABCD-123", this.order.getOrderNumber());
    }

    @Test
    public void testAccessSupplier() {
        assertNull(this.order.getSupplier());
        this.order.setSupplier(new Supplier());
        assertEquals(new Supplier(), this.order.getSupplier());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(this.order.getCreatedAt());
        this.order.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), this.order.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(this.order.getLastUpdated());
        this.order.setLastUpdated(LocalDateTime.of(2001, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), this.order.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(this.order.getVersion());
        this.order.setVersion(1);
        assertEquals(1, this.order.getVersion());
    }
}
