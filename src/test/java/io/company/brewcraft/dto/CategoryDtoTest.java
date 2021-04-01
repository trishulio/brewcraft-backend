package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryDtoTest {

    private CategoryDto category;

    @BeforeEach
    public void init() {
        category = new CategoryDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        Long parentCategoryId = 2L;
        String name = "testName";
        int version = 1;

        CategoryDto category = new CategoryDto(id, parentCategoryId, name, version);
        
        assertSame(id, category.getId());
        assertSame(parentCategoryId, category.getParentCategoryId());
        assertSame(name, category.getName());
        assertSame(version, category.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        category.setId(id);
        assertSame(id, category.getId());
    }
    
    @Test
    public void testGetSetParentCategoryId() {
        Long parentCategoryId = 1L;
        category.setParentCategoryId(parentCategoryId);
        assertSame(parentCategoryId, category.getParentCategoryId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        category.setName(name);
        assertSame(name, category.getName());
    }
  
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        category.setVersion(version);
        assertSame(version, category.getVersion());
    }
}
