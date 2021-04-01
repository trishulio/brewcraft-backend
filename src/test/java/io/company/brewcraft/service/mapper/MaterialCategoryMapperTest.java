package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.pojo.Category;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


public class MaterialCategoryMapperTest {

    private MaterialCategoryMapper materialCategoryMapper;

    @BeforeEach
    public void init() {
        materialCategoryMapper = MaterialCategoryMapper.INSTANCE;
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        MaterialCategoryEntity entity = new MaterialCategoryEntity(1L, "testName", new MaterialCategoryEntity(2L, null, null, null, null, null, null), Set.of(new MaterialCategoryEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Category category = materialCategoryMapper.fromEntity(entity, new CycleAvoidingMappingContext());

        Category expected = new Category(1L, "testName", new Category(2L, null, null, null, null, null, null), Set.of(new Category(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        expected.getParentCategory().addSubcategory(expected);
        expected.getSubcategories().stream().findFirst().get().setParentCategory(expected);
        
        assertReflectionEquals(expected, category);
    }

    @Test
    public void testFromDto_ReturnPojo_WhenDtoIsNotNull() {
        CategoryDto dto = new CategoryDto(1L, 2L, "testName", 1);
        Category category = materialCategoryMapper.fromDto(dto);
        
        Category expected = new Category(1L, "testName", null, null, null, null, 1);
        
        assertEquals(expected, category);
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Category category = new Category(1L, "testName", new Category(2L, null, null, null, null, null, null), Set.of(new Category()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        CategoryDto dto = materialCategoryMapper.toDto(category);

        assertEquals(new CategoryDto(1L, 2L, "testName", 1), dto);
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        Category material = new Category(1L, "testName", new Category(2L, null, null, null, null, null, null), Set.of(new Category(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategoryEntity entity = materialCategoryMapper.toEntity(material, new CycleAvoidingMappingContext());

        MaterialCategoryEntity expected = new MaterialCategoryEntity(1L, "testName", new MaterialCategoryEntity(2L, null, null, null, null, null, null), Set.of(new MaterialCategoryEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        expected.getParentCategory().addSubcategory(expected);
        expected.getSubcategories().stream().findFirst().get().setParentCategory(expected);
        
        assertReflectionEquals(expected, entity);
    }
}
