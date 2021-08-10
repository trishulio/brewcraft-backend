package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMaterialLotDtoTest {

    private UpdateMaterialLotDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateMaterialLotDto();
    }

    @Test
    public void testAllArgsConstructor_SetsValuesAllFields() {
        dto = new UpdateMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10.00")), 2L, 3L, 1);

        assertEquals(1L, dto.getId());
        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), dto.getQuantity());
        assertEquals(2L, dto.getInvoiceItemId());
        assertEquals(3L, dto.getStorageId());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());
        dto.setId(1L);
        assertEquals(1L, dto.getId());
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

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(2);
        assertEquals(2, dto.getVersion());
    }
}
