package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductCategoryTest {

    private ProductCategory productCategoryEntity;

    @BeforeEach
    public void init() {
        productCategoryEntity = new ProductCategory();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        ProductCategory parentCategory = new ProductCategory();
        Set<ProductCategory> subcategories = Set.of();       
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        ProductCategory productCategoryEntity = new ProductCategory(id, name, parentCategory, subcategories, created, lastUpdated, version);
        
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
        productCategoryEntity.setId(id);
        assertSame(id, productCategoryEntity.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        productCategoryEntity.setName(name);
        assertSame(name, productCategoryEntity.getName());
    }
    
    @Test
    public void testGetSetParentCategory() {
        ProductCategory parentCategory = new ProductCategory();
        productCategoryEntity.setParentCategory(parentCategory);
        assertSame(parentCategory, productCategoryEntity.getParentCategory());
    }
    
    @Test
    public void testGetSetSubcategories() {
        Set<ProductCategory> subcategories = Set.of();       
        productCategoryEntity.setSubcategories(subcategories);
        assertSame(subcategories, productCategoryEntity.getSubcategories());
    }
    
    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        productCategoryEntity.setCreatedAt(created);
        assertSame(created, productCategoryEntity.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        productCategoryEntity.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, productCategoryEntity.getLastUpdated());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        productCategoryEntity.setVersion(version);
        assertSame(version, productCategoryEntity.getVersion());
    }
    
    @Test
    public void testGetDescendantCategoryIds() {
        Set<ProductCategory> subcategories = Set.of(
                new ProductCategory(1L, null, null,
                        Set.of(new ProductCategory(3L),
                                new ProductCategory(4L)),
                        null, null, null),
                new ProductCategory(2L));

        ProductCategory category = new ProductCategory(null, null, null, subcategories, null, null, null);

        Set<Long> result = category.getDescendantCategoryIds();
        
        assertEquals(4, result.size());
        assertTrue(result.containsAll(List.of(1L, 2L, 3L, 4L)));       
    }
}
