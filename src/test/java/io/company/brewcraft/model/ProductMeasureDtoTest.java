package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.ProductMeasureDto;

public class ProductMeasureDtoTest {
    
    ProductMeasureDto productMeasuredto;
    
    @BeforeEach
    public void init() {
        productMeasuredto = new ProductMeasureDto();
    }
    
    @Test
    public void testConstructor() {
        productMeasuredto = new ProductMeasureDto("abv", "100");
        
        assertEquals("abv", productMeasuredto.getName());
        assertEquals("100", productMeasuredto.getValue());
    }
    
    @Test
    public void testGetSetName() {
        productMeasuredto.setName("abv");
        assertEquals("abv", productMeasuredto.getName());
    }
    
    @Test
    public void testGetSetValue() {
        productMeasuredto.setValue("100");
        assertEquals("100", productMeasuredto.getValue());
    }

}
