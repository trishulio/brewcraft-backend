package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateSkuMaterialDtoTest {

    private UpdateSkuMaterialDto updateSkuMaterialDto;

    @BeforeEach
    public void init() {
        updateSkuMaterialDto = new UpdateSkuMaterialDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Long materialId = 3L;
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
        int version = 1;

        UpdateSkuMaterialDto updateSkuMaterialDto = new UpdateSkuMaterialDto(id, materialId, quantity, version);

        assertEquals(1L, updateSkuMaterialDto.getId());
        assertEquals(3L, updateSkuMaterialDto.getMaterialId());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), updateSkuMaterialDto.getQuantity());
        assertEquals(1, updateSkuMaterialDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        updateSkuMaterialDto.setId(1L);
        assertEquals(1L, updateSkuMaterialDto.getId());
    }

    @Test
    public void testGetSetMaterialId() {
        updateSkuMaterialDto.setMaterialId(3L);
        assertEquals(3L, updateSkuMaterialDto.getMaterialId());
    }

    @Test
    public void testGetSetQuantity() {
        updateSkuMaterialDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), updateSkuMaterialDto.getQuantity());
    }

    @Test
    public void testGetSetVersion() {
        updateSkuMaterialDto.setVersion(1);
        assertEquals(1, updateSkuMaterialDto.getVersion());
    }
}
