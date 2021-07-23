package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductMeasureTest {
    
    ProductMeasure productMeasure;
    
    @BeforeEach
    public void init() {
        productMeasure = new ProductMeasure();
    }
    
    @Test
    public void testConstructor() {
        productMeasure = new ProductMeasure(1L, "abv");
        
        assertEquals(1L, productMeasure.getId());
        assertEquals("abv", productMeasure.getName());
    }
    
    @Test
    public void testGetSetId() {
        productMeasure.setId(1L);
        assertEquals(1L, productMeasure.getId());
    }
    
    @Test
    public void testGetSetName() {
        productMeasure.setName("abv");
        assertEquals("abv", productMeasure.getName());
    }

}
