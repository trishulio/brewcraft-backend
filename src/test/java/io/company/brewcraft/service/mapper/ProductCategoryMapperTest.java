package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddCategoryDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.model.ProductCategory;

public class ProductCategoryMapperTest {

    private ProductCategoryMapper productCategoryMapper;

    @BeforeEach
    public void init() {
        productCategoryMapper = ProductCategoryMapper.INSTANCE;
    }

    @Test
    public void testoDto_ReturnsDto() {
        ProductCategory entity = new ProductCategory(1L, "testName", new ProductCategory(2L), Set.of(new ProductCategory(3L)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        CategoryDto category = productCategoryMapper.toDto(entity);

        assertEquals(new CategoryDto(1L, 2L, "testName", 1), category);
    }

    @Test
    public void testFromDto_ReturnsEntity() {
        CategoryDto dto = new CategoryDto(1L, 2L, "testName", 1);
        ProductCategory category = productCategoryMapper.fromDto(dto);

        ProductCategory expected = new ProductCategory(1L, "testName", null, null, null, null, 1);

        assertEquals(expected, category);
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddCategoryDto dto = new AddCategoryDto(1L, "testName");
        ProductCategory category = productCategoryMapper.fromDto(dto);

        ProductCategory expected = new ProductCategory(null, "testName", null, null, null, null, null);

        assertEquals(expected, category);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateCategoryDto dto = new UpdateCategoryDto(1L, "testName", 1);
        ProductCategory category = productCategoryMapper.fromDto(dto);

        ProductCategory expected = new ProductCategory(null, "testName", null, null, null, null, 1);

        assertEquals(expected, category);
    }

}
