package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddMaterialDtoTest {
    private AddMaterialDto material;

    @BeforeEach
    public void init() {
        material = new AddMaterialDto();
    }

    @Test
    public void testConstructor() {
        String name = "testName";
        String description = "testDesc";
        Long categoryId = 1L;
        String upc = "testUPC";
        String baseQuantityUnit = "g";
        String imageSrc = "http://www.test.com";

        AddMaterialDto material = new AddMaterialDto(name, description, categoryId, upc, baseQuantityUnit, imageSrc);

        assertSame(name, material.getName());
        assertSame(description, material.getDescription());
        assertSame(categoryId, material.getCategoryId());
        assertSame(upc, material.getUpc());
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
        assertSame(imageSrc, material.getImageSrc());
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
    public void testGetSetUpc() {
        String upc = "testUpc";
        material.setUpc(upc);
        assertSame(upc, material.getUpc());
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
}
