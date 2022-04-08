package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdatePurchaseOrderDtoTest {
    private UpdatePurchaseOrderDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdatePurchaseOrderDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new UpdatePurchaseOrderDto(1L, "ORDER_1", 2L, 1);

        assertEquals(1L, dto.getId());
        assertEquals("ORDER_1", dto.getOrderNumber());
        assertEquals(2L, dto.getSupplierId());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        UpdatePurchaseOrderDto dto = new UpdatePurchaseOrderDto(1L);

        assertEquals(1L, dto.getId());
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

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());

        dto.setVersion(99);
        assertEquals(99, dto.getVersion());
    }
}
