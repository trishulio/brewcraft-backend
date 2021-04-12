package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialCategoryTest {

    private MaterialCategory materialCategoryEntityTest;

    @BeforeEach
    public void init() {
        materialCategoryEntityTest = new MaterialCategory();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        MaterialCategory parentCategory = new MaterialCategory();
        Set<MaterialCategory> subcategories = Set.of();       
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        MaterialCategory productCategoryEntity = new MaterialCategory(id, name, parentCategory, subcategories, created, lastUpdated, version);
        
        assertSame(id, productCategoryEntity.getId());
        assertSame(name, productCategoryEntity.getName());
        assertSame(parentCategory, productCategoryEntity.getParentCategory());
        assertSame(subcategories, productCategoryEntity.getSubcategories());
        assertSame(created, productCategoryEntity.getCreatedAt());
        assertSame(lastUpdated, productCategoryEntity.getLastUpdated());
        assertSame(version, productCategoryEntity.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        materialCategoryEntityTest.setId(id);
        assertSame(id, materialCategoryEntityTest.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        materialCategoryEntityTest.setName(name);
        assertSame(name, materialCategoryEntityTest.getName());
    }
    
    @Test
    public void testGetSetParentCategory() {
        MaterialCategory parentCategory = new MaterialCategory();
        materialCategoryEntityTest.setParentCategory(parentCategory);
        assertSame(parentCategory, materialCategoryEntityTest.getParentCategory());
    }
    
    @Test
    public void testGetSetSubcategories() {
        Set<MaterialCategory> subcategories = Set.of();       
        materialCategoryEntityTest.setSubcategories(subcategories);
        assertSame(subcategories, materialCategoryEntityTest.getSubcategories());
    }
    
    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        materialCategoryEntityTest.setCreatedAt(created);
        assertSame(created, materialCategoryEntityTest.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        materialCategoryEntityTest.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, materialCategoryEntityTest.getLastUpdated());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialCategoryEntityTest.setVersion(version);
        assertSame(version, materialCategoryEntityTest.getVersion());
    }
}
