package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddSkuMaterialDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuMaterialDto;
import io.company.brewcraft.dto.UpdateSkuMaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class SkuMaterialMapperTest {

    private SkuMaterialMapper skuMaterialMapper;

    @BeforeEach
    public void init() {
        skuMaterialMapper = SkuMaterialMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddSkuMaterialDto dto = new AddSkuMaterialDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100)));

        SkuMaterial skuMaterial = skuMaterialMapper.fromDto(dto);

        SkuMaterial expectedSkuMaterial = new SkuMaterial(null, null, new Material(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), null, null, null);

        assertEquals(expectedSkuMaterial, skuMaterial);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateSkuMaterialDto dto = new UpdateSkuMaterialDto(1L, 2L, new QuantityDto("hl", BigDecimal.valueOf(100)), 1);

        SkuMaterial skuMaterial = skuMaterialMapper.fromDto(dto);

        SkuMaterial expectedSkuMaterial = new SkuMaterial(1L, null, new Material(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), null, null, 1);

        assertEquals(expectedSkuMaterial, skuMaterial);
    }

    @Test
    public void testToDto_ReturnsDto() {
        SkuMaterial skuMaterial = new SkuMaterial(1L, new Sku(4L), new Material(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        SkuMaterialDto dto = skuMaterialMapper.toDto(skuMaterial);

        assertEquals(new SkuMaterialDto(1L, new MaterialDto(2L), new QuantityDto("hl", BigDecimal.valueOf(100)), 1), dto);
    }

}
