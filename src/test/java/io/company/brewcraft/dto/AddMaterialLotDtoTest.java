package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddMaterialLotDtoTest {

    private AddMaterialLotDto dto;

    @BeforeEach
    public void init() {
        dto = new AddMaterialLotDto();
    }

    @Test
    public void testAllArgsConstructor_SetsValuesAllFields() {
        dto = new AddMaterialLotDto("LOT_1", new QuantityDto("kg", new BigDecimal("10.00")), 1L, 2L, 3L);

        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), dto.getQuantity());
        assertEquals(1L, dto.getMaterialId());
        assertEquals(2L, dto.getInvoiceItemId());
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
    public void testAccessMaterialId() {
        assertNull(dto.getMaterialId());
        dto.setMaterialId(100L);
        assertEquals(100L, dto.getMaterialId());
    }

    @Test
    public void testAccessInvoiceItemId() {
        assertNull(dto.getInvoiceItemId());
        dto.setInvoiceItemId(200L);
        assertEquals(200L, dto.getInvoiceItemId());
    }

    @Test
    public void testAccessStorageId() {
        assertNull(dto.getStorageId());
        dto.setStorageId(300L);
        assertEquals(300L, dto.getStorageId());
    }
}
