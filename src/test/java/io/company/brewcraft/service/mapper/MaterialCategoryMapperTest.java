package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.model.MaterialCategory;

public class MaterialCategoryMapperTest {

    private MaterialCategoryMapper materialCategoryMapper;

    @BeforeEach
    public void init() {
        materialCategoryMapper = MaterialCategoryMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnEntity_WhenDtoIsNotNull() {
        CategoryDto dto = new CategoryDto(1L, 2L, "testName", 1);
        MaterialCategory category = materialCategoryMapper.fromDto(dto);

        MaterialCategory expected = new MaterialCategory(1L, "testName", null, null, null, null, 1);

        assertEquals(expected, category);
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        MaterialCategory category = new MaterialCategory(1L, "testName", new MaterialCategory(2L), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        CategoryDto dto = materialCategoryMapper.toDto(category);

        assertEquals(new CategoryDto(1L, 2L, "testName", 1), dto);
    }
}
