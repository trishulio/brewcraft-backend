package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.pojo.MaterialCategory;

public class MaterialCategoryTest {

    private MaterialCategory materialCategory;

    @BeforeEach
    public void init() {
        materialCategory = new MaterialCategory();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        MaterialCategory parentCategory = new MaterialCategory();
        Set<MaterialCategory> subcategories = Set.of(new MaterialCategory());
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();
        int version = 1;

        MaterialCategory materialCategory = new MaterialCategory(id, name, parentCategory, subcategories, created, lastUpdated, version);
        
        assertSame(id, materialCategory.getId());
        assertSame(name, materialCategory.getName());
        assertSame(parentCategory, materialCategory.getParentCategory());
        assertEquals(subcategories, materialCategory.getSubcategories());
        assertSame(created, materialCategory.getCreatedAt());
        assertSame(lastUpdated, materialCategory.getLastUpdated());
        assertSame(version, materialCategory.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        materialCategory.setId(id);
        assertSame(id, materialCategory.getId());
    }
    
    @Test
    public void testGetSetName() {
        String name = "testName";
        materialCategory.setName(name);
        assertSame(name, materialCategory.getName());
    }
    
    @Test
    public void testGetSetParentCategory() {
        MaterialCategory parentCategory = new MaterialCategory();
        materialCategory.setParentCategory(parentCategory);
        assertSame(parentCategory, materialCategory.getParentCategory());
    }
    
    @Test
    public void testGetSetSubcategories() {
        Set<MaterialCategory> subcategories = Set.of(new MaterialCategory());
        materialCategory.setSubcategories(subcategories);
        assertEquals(subcategories, materialCategory.getSubcategories());
    }
    
    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        materialCategory.setCreatedAt(created);
        assertSame(created, materialCategory.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        materialCategory.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, materialCategory.getLastUpdated());
    }
  
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialCategory.setVersion(version);
        assertSame(version, materialCategory.getVersion());
    }
    
    @Test
    public void testGetDescendantCategoryIds() {
        Set<MaterialCategory> subcategories = Set.of(
                new MaterialCategory(1L, null, null,
                        Set.of(new MaterialCategory(3L, null, null, null, null, null, null),
                                new MaterialCategory(4L, null, null, null, null, null, null)),
                        null, null, null),
                new MaterialCategory(2L, null, null, null, null, null, null));

        MaterialCategory materialCategory = new MaterialCategory(null, null, null, subcategories, null, null, null);

        Set<Long> result = materialCategory.getDescendantCategoryIds();
        
        assertEquals(4, result.size());
        assertTrue(result.containsAll(List.of(1L, 2L, 3L, 4L)));       
    }
}
