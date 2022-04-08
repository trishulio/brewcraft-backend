package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.AddProcurementMaterialLotDto;

public class AddProcurementMaterialLotDtoTest {
    private AddProcurementMaterialLotDto dto;

    @BeforeEach
    public void init() {
        dto = new AddProcurementMaterialLotDto();
    }

    @Test
    public void testAllArgsConstructor_SetsValuesAllFields() {
        dto = new AddProcurementMaterialLotDto("LOT_1", new QuantityDto("kg", new BigDecimal("10.00")), 3L);

        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), dto.getQuantity());
        assertEquals(3L, dto.getStorageId());
    }

    @Test
    public void testAccessLotNumber() {
        assertNull(dto.getLotNumber());
        dto.setLotNumber("LOT_1");
        assertEquals("LOT_1", dto.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(dto.getQuantity());
        dto.setQuantity(new QuantityDto("kg", new BigDecimal("10.00")));
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), dto.getQuantity());
    }

    @Test
    public void testAccessStorageId() {
        assertNull(dto.getStorageId());
        dto.setStorageId(300L);
        assertEquals(300L, dto.getStorageId());
    }
}
