package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.util.SupportedUnits;

public class MaterialMapperTest {

    private MaterialMapper materialMapper;

    @BeforeEach
    public void init() {
        materialMapper = MaterialMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsEntity_WhenDtoIsNotNull() {
        MaterialDto dto = new MaterialDto(1L, "testMaterial", "testDescription", new CategoryDto(1L), null, null, "testUPC", "kg", 1);
        Material material = materialMapper.fromDto(dto);

        assertEquals(new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, null, null, 1), material);
    }

    @Test
    public void testFromDto_ReturnsEntityWithId_WhenIdIsNotNull() {
        Material material = materialMapper.fromDto(1L);
        assertEquals(1L, material.getId());
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        Material material = materialMapper.fromDto((Long) null);
        assertNull(material);
    }

    @Test
    public void testToDto_ReturnsDto_WhenEntityIsNotNull() {
        Material material = new Material(1L, "testMaterial", "testDescription", new MaterialCategory(1L), "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialDto dto = materialMapper.toDto(material);

        assertEquals(new MaterialDto(1L, "testMaterial", "testDescription", new CategoryDto(1L), null, null, "testUPC", "kg", 1), dto);
    }

    @Test
    public void testBeforeDto_setsAllCategories() {
        MaterialDto materialDto = new MaterialDto(1L, "material1", null, null, null, null, null, null, null);

        MaterialCategory rootCategory = new MaterialCategory(1L, "root", null, null, null, null, null);
        MaterialCategory subcategory1 = new MaterialCategory(2L, "subcategory1", rootCategory, null, null, null, null);
        MaterialCategory subcategory2 = new MaterialCategory(3L, "subcategory2", subcategory1, null, null, null, null);

        Material material = new Material(1L, "material1", null, subcategory2, null, null, null, null, null);

        materialMapper.beforetoDto(materialDto, material);

        assertEquals(rootCategory.getId(), materialDto.getMaterialClass().getId());
        assertEquals(subcategory1.getId(), materialDto.getCategory().getId());
        assertEquals(subcategory2.getId(), materialDto.getSubcategory().getId());
    }

 }
