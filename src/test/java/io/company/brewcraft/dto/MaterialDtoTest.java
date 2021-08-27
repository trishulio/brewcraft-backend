package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

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
        CategoryDto materialClass = new CategoryDto();
        CategoryDto category = new CategoryDto();
        CategoryDto subcategory = new CategoryDto();
        String upc = "testUPC";
        String baseQuantityUnit = "g";
        String imageSrc = "http://www.test.com";
        int version = 1;

        MaterialDto material = new MaterialDto(id, name, description, materialClass, category, subcategory, upc, baseQuantityUnit, imageSrc, version);

        assertSame(id, material.getId());
        assertSame(name, material.getName());
        assertSame(description, material.getDescription());
        assertSame(materialClass, material.getMaterialClass());
        assertSame(category, material.getCategory());
        assertSame(subcategory, material.getSubcategory());
        assertSame(upc, material.getUPC());
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
        assertSame(imageSrc, material.getImageSrc());
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
        CategoryDto materialClass = new CategoryDto();
        material.setMaterialClass(materialClass);
        assertSame(materialClass, material.getMaterialClass());
    }

    @Test
    public void testGetSetCategory() {
        CategoryDto category = new CategoryDto();
        material.setCategory(category);
        assertSame(category, material.getCategory());
    }

    @Test
    public void testGetSetSubcategory() {
        CategoryDto subcategory = new CategoryDto();
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
    public void testGetSetImageSrc() {
        material.setImageSrc("http://www.test.com");
        assertSame("http://www.test.com", material.getImageSrc());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        material.setVersion(version);
        assertSame(version, material.getVersion());
    }
}
