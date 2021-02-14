package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialDtoTest {

    private MaterialDto material;

    @BeforeEach
    public void init() {
        material = new MaterialDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        MaterialCategoryDto materialClass = new MaterialCategoryDto();
        MaterialCategoryDto category = new MaterialCategoryDto();
        MaterialCategoryDto subcategory = new MaterialCategoryDto();
        String upc = "testUPC";
        String baseQuantityUnit = "g";
        int version = 1;

        MaterialDto material = new MaterialDto(id, name, description, materialClass, category, subcategory, upc, baseQuantityUnit, version);
        
        assertSame(id, material.getId());
        assertSame(name, material.getName());
        assertSame(description, material.getDescription());
        assertSame(materialClass, material.getMaterialClass());
        assertSame(category, material.getCategory());
        assertSame(subcategory, material.getSubcategory());
        assertSame(upc, material.getUPC());
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
        assertSame(version, material.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        material.setId(id);
        assertSame(id, material.getId());
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
    public void testGetSetMaterialClass() {
        MaterialCategoryDto materialClass = new MaterialCategoryDto();
        material.setMaterialClass(materialClass);
        assertSame(materialClass, material.getMaterialClass());
    }
    
    @Test
    public void testGetSetCategory() {
        MaterialCategoryDto category = new MaterialCategoryDto();
        material.setCategory(category);
        assertSame(category, material.getCategory());
    }
    
    @Test
    public void testGetSetSubcategory() {
        MaterialCategoryDto subcategory = new MaterialCategoryDto();
        material.setSubcategory(subcategory);
        assertSame(subcategory, material.getSubcategory());
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
