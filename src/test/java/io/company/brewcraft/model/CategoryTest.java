package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.pojo.Category;

public class CategoryTest {

    private Category category;

    @BeforeEach
    public void init() {
        category = new Category();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        Category parentCategory = new Category();
        Set<Category> subcategories = Set.of(new Category());
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();
        int version = 1;

        Category category = new Category(id, name, parentCategory, subcategories, created, lastUpdated, version);
        
        assertSame(id, category.getId());
        assertSame(name, category.getName());
        assertSame(parentCategory, category.getParentCategory());
        assertEquals(subcategories, category.getSubcategories());
        assertSame(created, category.getCreatedAt());
        assertSame(lastUpdated, category.getLastUpdated());
        assertSame(version, category.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        category.setId(id);
        assertSame(id, category.getId());
    }
    
    @Test
    public void testGetSetName() {
        String name = "testName";
        category.setName(name);
        assertSame(name, category.getName());
    }
    
    @Test
    public void testGetSetParentCategory() {
        Category parentCategory = new Category();
        category.setParentCategory(parentCategory);
        assertSame(parentCategory, category.getParentCategory());
    }
    
    @Test
    public void testGetSetSubcategories() {
        Set<Category> subcategories = Set.of(new Category());
        category.setSubcategories(subcategories);
        assertEquals(subcategories, category.getSubcategories());
    }
    
    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        category.setCreatedAt(created);
        assertSame(created, category.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        category.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, category.getLastUpdated());
    }
  
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        category.setVersion(version);
        assertSame(version, category.getVersion());
    }
    
    @Test
    public void testGetDescendantCategoryIds() {
        Set<Category> subcategories = Set.of(
                new Category(1L, null, null,
                        Set.of(new Category(3L, null, null, null, null, null, null),
                                new Category(4L, null, null, null, null, null, null)),
                        null, null, null),
                new Category(2L, null, null, null, null, null, null));

        Category category = new Category(null, null, null, subcategories, null, null, null);

        Set<Long> result = category.getDescendantCategoryIds();
        
        assertEquals(4, result.size());
        assertTrue(result.containsAll(List.of(1L, 2L, 3L, 4L)));       
    }
}
