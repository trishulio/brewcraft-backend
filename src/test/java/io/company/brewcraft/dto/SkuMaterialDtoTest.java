package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SkuMaterialDtoTest {

    private SkuMaterialDto skuMaterialDto;

    @BeforeEach
    public void init() {
        skuMaterialDto = new SkuMaterialDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        MaterialDto material = new MaterialDto(3L);
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
        int version = 1;

        SkuMaterialDto skuDto = new SkuMaterialDto(id, material, quantity, version);

        assertEquals(1L, skuDto.getId());
        assertEquals(new MaterialDto(3L), skuDto.getMaterial());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), skuDto.getQuantity());
        assertEquals(1, skuDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        skuMaterialDto.setId(1L);
        assertEquals(1L, skuMaterialDto.getId());
    }

    @Test
    public void testGetSetMaterial() {
        skuMaterialDto.setMaterial(new MaterialDto(3L));
        assertEquals(new MaterialDto(3L), skuMaterialDto.getMaterial());
    }

    @Test
    public void testGetSetQuantity() {
        skuMaterialDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), skuMaterialDto.getQuantity());
    }

    @Test
    public void testGetSetVersion() {
        skuMaterialDto.setVersion(1);
        assertEquals(1, skuMaterialDto.getVersion());
    }
}
