package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.utils.SupportedUnits;

public class MaterialMapperTest {

    private MaterialMapper materialMapper;

    @BeforeEach
    public void init() {
        materialMapper = MaterialMapper.INSTANCE;
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        MaterialEntity entity = new MaterialEntity(1L, "testMaterial", "testDescription", new MaterialCategoryEntity(1L, null, null, null, null, null, null), "testUPC", new UnitEntity("kg"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Material material = materialMapper.fromEntity(entity, new CycleAvoidingMappingContext());

        assertEquals(new Material(1L, "testMaterial", "testDescription", new Category(1L, null, null, null, null, null, null), "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1), material);
    }

    @Test
    public void testFromDto_ReturnPojo_WhenDtoIsNotNull() {
        MaterialDto dto = new MaterialDto(1L, "testMaterial", "testDescription", new CategoryDto(1L, null, null, null), null, null, "testUPC", "kg", 1);
        Material material = materialMapper.fromDto(dto);
        
        assertEquals(new Material(1L, "testMaterial", "testDescription", new Category(1L, null, null, null, null, null, null), "testUPC", SupportedUnits.KILOGRAM, null, null, 1), material);
    }

    @Test
    public void testFromDto_ReturnsPojoWithMaterialId_WhenIdIsNotNull() {
        Material material = materialMapper.fromDto(1L);
        assertEquals(1L, material.getId());
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        Material material = materialMapper.fromDto((Long) null);
        assertNull(material);
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Material material = new Material(1L, "testMaterial", "testDescription", new Category(1L, null, null, null, null, null, null), "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialDto dto = materialMapper.toDto(material);

        assertEquals(new MaterialDto(1L, "testMaterial", "testDescription", new CategoryDto(1L, null, null, null), null, null, "testUPC", "kg", 1), dto);
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        Material material = new Material(1L, "testMaterial", "testDescription", new Category(1L, null, null, null, null, null, null), "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialEntity entity = materialMapper.toEntity(material, new CycleAvoidingMappingContext());

        assertEquals(new MaterialEntity(1L, "testMaterial", "testDescription", new MaterialCategoryEntity(1L, null, null, null, null, null, null), "testUPC", new UnitEntity("kg"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1), entity);
    }
    
    @Test
    public void testBeforeDto_setsAllCategories() {
        MaterialDto materialDto = new MaterialDto(1L, "material1", null, null, null, null, null, null, null);
        
        Category rootCategory = new Category(1L, "root", null, null, null, null, null);
        Category subcategory1 = new Category(2L, "subcategory1", rootCategory, null, null, null, null);
        Category subcategory2 = new Category(3L, "subcategory2", subcategory1, null, null, null, null);

        Material material = new Material(1L, "material1", null, subcategory2, null, null, null, null, null);
        
        materialMapper.beforetoDto(materialDto, material);

        assertEquals(rootCategory.getId(), materialDto.getMaterialClass().getId());
        assertEquals(subcategory1.getId(), materialDto.getCategory().getId());
        assertEquals(subcategory2.getId(), materialDto.getSubcategory().getId());
    }
    
 }
