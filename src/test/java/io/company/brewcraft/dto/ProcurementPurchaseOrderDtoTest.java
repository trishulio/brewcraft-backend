package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;

public class ProcurementPurchaseOrderDtoTest {
    private ProcurementPurchaseOrderDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementPurchaseOrderDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new ProcurementPurchaseOrderDto(1L, "ORDER_1", new SupplierDto(2L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        assertEquals(1L, dto.getId());
        assertEquals("ORDER_1", dto.getOrderNumber());
        assertEquals(new SupplierDto(2L), dto.getSupplier());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getLastUpdated());
        assertEquals(1, dto.getVersion());
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

    @Test
    public void testAccessCreatedAt() {
        assertNull(dto.getCreatedAt());
        dto.setCreatedAt(LocalDateTime.of(2011, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2011, 1, 1, 0, 0), dto.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(dto.getLastUpdated());
        dto.setLastUpdated(LocalDateTime.of(2011, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2011, 1, 1, 0, 0), dto.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());

        dto.setVersion(99);
        assertEquals(99, dto.getVersion());
    }
}
