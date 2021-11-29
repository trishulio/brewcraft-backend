package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddProcurementPurchaseOrderDtoTest {
    private AddPurchaseOrderDto dto;

    @BeforeEach
    public void init() {
        dto = new AddPurchaseOrderDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new AddPurchaseOrderDto("ORDER_1", 2L);

        assertEquals("ORDER_1", dto.getOrderNumber());
        assertEquals(2L, dto.getSupplierId());
    }

    @Test
    public void testAccessOrderNumber() {
        assertNull(dto.getOrderNumber());

        dto.setOrderNumber("ORDER_99");
        assertEquals("ORDER_99", dto.getOrderNumber());
    }

    @Test
    public void testAccessSupplierId() {
        assertNull(dto.getSupplierId());

        dto.setSupplierId(99L);
        assertEquals(99L, dto.getSupplierId());
    }
}
