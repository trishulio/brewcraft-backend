package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMaterialDtoTest {

    private UpdateMaterialDto material;

    @BeforeEach
    public void init() {
        material = new UpdateMaterialDto();
    }

    @Test
    public void testConstructor() {
        String name = "testName";
        String description = "testDesc";
        Long categoryId = 1L;
        String upc = "testUPC";
        String baseQuantityUnit = "g";
        int version = 1;

        UpdateMaterialDto material = new UpdateMaterialDto(name, description, categoryId, upc, baseQuantityUnit, version);

        assertSame(name, material.getName());
        assertSame(description, material.getDescription());
        assertSame(categoryId, material.getCategoryId());
        assertSame(upc, material.getUPC());
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
        assertSame(version, material.getVersion());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        material.setName(name);
        assertSame(name, material.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        material.setDescription(description);
        assertSame(description, material.getDescription());
    }

    @Test
    public void testGetSetCategoryId() {
        Long categoryId = 1L;
        material.setCategoryId(categoryId);
        assertSame(categoryId, material.getCategoryId());
    }

    @Test
    public void testGetSetUPC() {
        String upc = "testUpc";
        material.setUPC(upc);
        assertSame(upc, material.getUPC());
    }

    @Test
    public void testGetSetBaseQuantityUnit() {
        String baseQuantityUnit = "g";
        material.setBaseQuantityUnit(baseQuantityUnit);
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        material.setVersion(version);
        assertSame(version, material.getVersion());
    }
}
