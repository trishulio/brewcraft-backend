package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOrderDtoTest {

    private PurchaseOrderDto dto;

    @BeforeEach
    public void init() {
        dto = new PurchaseOrderDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new PurchaseOrderDto(1L, "ORDER_1", new SupplierDto(2L));

        assertEquals(1L, dto.getId());
        assertEquals("ORDER_1", dto.getOrderNumber());
        assertEquals(new SupplierDto(2L), dto.getSupplier());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());

        dto.setId(99L);
        assertEquals(99L, dto.getId());
    }

    @Test
    public void testAccessOrderNumber() {
        assertNull(dto.getOrderNumber());

        dto.setOrderNumber("ORDER_99");
        assertEquals("ORDER_99", dto.getOrderNumber());
    }

    @Test
    public void testAccessSupplier() {
        assertNull(dto.getSupplier());

        dto.setSupplier(new SupplierDto(99L));
        assertEquals(new SupplierDto(99L), dto.getSupplier());
    }
}
