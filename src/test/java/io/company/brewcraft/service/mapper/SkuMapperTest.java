package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddSkuDto;
import io.company.brewcraft.dto.AddSkuMaterialDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.SkuMaterialDto;
import io.company.brewcraft.dto.UpdateSkuDto;
import io.company.brewcraft.dto.UpdateSkuMaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class SkuMapperTest {

    private SkuMapper skuMapper;

    @BeforeEach
    public void init() {
        skuMapper = SkuMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddSkuDto dto = new AddSkuDto("testName", "testDescription", 2L, List.of(new AddSkuMaterialDto(3L)), new QuantityDto("hl", BigDecimal.valueOf(100)));

        Sku sku = skuMapper.fromDto(dto);
        
        Sku expectedSku = new Sku(null, "testName", "testDescription", new Product(2L), List.of(new SkuMaterial(null, null, new Material(3L), null, null, null, null)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), null, null, null);

        assertEquals(expectedSku, sku);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateSkuDto dto = new UpdateSkuDto("testName", "testDescription", 2L, List.of(new UpdateSkuMaterialDto(3L)), new QuantityDto("hl", BigDecimal.valueOf(100)), 1);

        Sku sku = skuMapper.fromDto(dto);
        
        Sku expectedSku = new Sku(null, "testName", "testDescription", new Product(2L), List.of(new SkuMaterial(3L, null, null, null, null, null, null)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), null, null, 1);

        assertEquals(expectedSku, sku);
    }

    @Test
    public void testToDto_ReturnsDto() {
        Sku sku = new Sku(1L, "testName", "testDescription", new Product(2L), List.of(new SkuMaterial(3L)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        SkuDto dto = skuMapper.toDto(sku);

        assertEquals(new SkuDto(1L, "testName", "testDescription", new ProductDto(2L), List.of(new SkuMaterialDto(3L)), new QuantityDto("hl", BigDecimal.valueOf(100)), 1), dto);
    }

}
