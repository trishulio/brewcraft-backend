package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductMeasureValueTest {
    
    ProductMeasureValue productMeasureValue;
    
    @BeforeEach
    public void init() {
        productMeasureValue = new ProductMeasureValue();
    }
    
    @Test
    public void testConstructor() {
        productMeasureValue = new ProductMeasureValue(1L,new ProductMeasure(), "100", new Product());
        
        assertEquals(1L, productMeasureValue.getId());
        assertEquals(new ProductMeasure(), productMeasureValue.getProductMeasure());
        assertEquals("100", productMeasureValue.getValue());
        assertEquals(new Product(), productMeasureValue.getProduct());
    }
    
    @Test
    public void testGetSetId() {
        productMeasureValue.setId(1L);
        assertEquals(1L, productMeasureValue.getId());
    }
    
    @Test
    public void testGetSetProductMeasure() {
        productMeasureValue.setProductMeasure(new ProductMeasure());
        assertEquals(new ProductMeasure(), productMeasureValue.getProductMeasure());
    }
    
    @Test
    public void testGetSetValue() {
        productMeasureValue.setValue("100");
        assertEquals("100", productMeasureValue.getValue());
    }
    
    @Test
    public void testGetSetProduct() {
        productMeasureValue.setProduct(new Product());
        assertEquals(new Product(), productMeasureValue.getProduct());
    }
}
