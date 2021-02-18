package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MaterialCategoryDto;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.pojo.MaterialCategory;

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
        MaterialCategory category = materialCategoryMapper.fromEntity(entity, new CycleAvoidingMappingContext());

        MaterialCategory expected = new MaterialCategory(1L, "testName", new MaterialCategory(2L, null, null, null, null, null, null), Set.of(new MaterialCategory(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        expected.getParentCategory().addSubcategory(expected);
        expected.getSubcategories().stream().findFirst().get().setParentCategory(expected);
        
        assertReflectionEquals(expected, category);
    }

    @Test
    public void testFromDto_ReturnPojo_WhenDtoIsNotNull() {
        MaterialCategoryDto dto = new MaterialCategoryDto(1L, 2L, "testName", 1);
        MaterialCategory category = materialCategoryMapper.fromDto(dto);
        
        MaterialCategory expected = new MaterialCategory(1L, "testName", null, null, null, null, 1);
        
        assertEquals(expected, category);
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        MaterialCategory category = new MaterialCategory(1L, "testName", new MaterialCategory(2L, null, null, null, null, null, null), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategoryDto dto = materialCategoryMapper.toDto(category);

        assertEquals(new MaterialCategoryDto(1L, 2L, "testName", 1), dto);
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        MaterialCategory material = new MaterialCategory(1L, "testName", new MaterialCategory(2L, null, null, null, null, null, null), Set.of(new MaterialCategory(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategoryEntity entity = materialCategoryMapper.toEntity(material, new CycleAvoidingMappingContext());

        MaterialCategoryEntity expected = new MaterialCategoryEntity(1L, "testName", new MaterialCategoryEntity(2L, null, null, null, null, null, null), Set.of(new MaterialCategoryEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        expected.getParentCategory().addSubcategory(expected);
        expected.getSubcategories().stream().findFirst().get().setParentCategory(expected);
        
        assertReflectionEquals(expected, entity);
    }
}
