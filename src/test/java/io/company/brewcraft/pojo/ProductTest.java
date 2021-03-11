package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void init() {
        product = new Product();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        Category category = new Category();
        ProductMeasures targetMeasures = new ProductMeasures();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime deletedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Product product = new Product(id, name, description, category, targetMeasures, created, lastUpdated, deletedAt, version);
        
        assertSame(id, product.getId());
        assertSame(name, product.getName());
        assertSame(description, product.getDescription());
        assertSame(category, product.getCategory());
        assertSame(targetMeasures, product.getTargetMeasures());
        assertSame(created, product.getCreatedAt());
        assertSame(lastUpdated, product.getLastUpdated());
        assertSame(deletedAt, product.getDeletedAt());
        assertSame(version, product.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        product.setId(id);
        assertSame(id, product.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        product.setName(name);
        assertSame(name, product.getName());
    }
    
    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        product.setDescription(description);
        assertSame(description, product.getDescription());
    }
    
    @Test
    public void testGetSetCategory() {
        Category category = new Category();
        product.setCategory(category);
        assertSame(category, product.getCategory());
    }
    
    @Test
    public void testGetSetTargetMeasures() {
        ProductMeasures targetMeasures = new ProductMeasures();
        product.setTargetMeasures(targetMeasures);
        assertSame(targetMeasures, product.getTargetMeasures());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        product.setCreatedAt(created);
        assertSame(created, product.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        product.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, product.getLastUpdated());
    }
    
    @Test
    public void testGetSetDeletedAt() {
        LocalDateTime deletedAt = LocalDateTime.now();
        product.setDeletedAt(deletedAt);
        assertSame(deletedAt, product.getDeletedAt());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        product.setVersion(version);
        assertSame(version, product.getVersion());
    }

}
