package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialCategoryDtoTest {

    private MaterialCategoryDto materialCategory;

    @BeforeEach
    public void init() {
        materialCategory = new MaterialCategoryDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        Long parentCategoryId = 2L;
        String name = "testName";
        int version = 1;

        MaterialCategoryDto materialCategory = new MaterialCategoryDto(id, parentCategoryId, name, version);
        
        assertSame(id, materialCategory.getId());
        assertSame(parentCategoryId, materialCategory.getParentCategoryId());
        assertSame(name, materialCategory.getName());
        assertSame(version, materialCategory.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        materialCategory.setId(id);
        assertSame(id, materialCategory.getId());
    }
    
    @Test
    public void testGetSetParentCategoryId() {
        Long parentCategoryId = 1L;
        materialCategory.setParentCategoryId(parentCategoryId);
        assertSame(parentCategoryId, materialCategory.getParentCategoryId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        materialCategory.setName(name);
        assertSame(name, materialCategory.getName());
    }
  
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialCategory.setVersion(version);
        assertSame(version, materialCategory.getVersion());
    }
}
