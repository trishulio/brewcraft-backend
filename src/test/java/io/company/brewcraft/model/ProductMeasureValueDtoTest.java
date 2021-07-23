package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.ProductMeasureDto;
import io.company.brewcraft.dto.ProductMeasureValueDto;

public class ProductMeasureValueDtoTest {
    
    ProductMeasureValueDto productMeasureDto;
    
    @BeforeEach
    public void init() {
        productMeasureDto = new ProductMeasureValueDto();
    }
    
    @Test
    public void testConstructor() {
        productMeasureDto = new ProductMeasureValueDto(1L, new ProductMeasureDto(2L, "abv"), "100");
        
        assertEquals(1L, productMeasureDto.getId());
        assertEquals(new ProductMeasureDto(2L, "abv"), productMeasureDto.getMeasure());
        assertEquals("100", productMeasureDto.getValue());
    }
    
    @Test
    public void testGetSetId() {
        productMeasureDto.setId(1L);
        assertEquals(1L, productMeasureDto.getId());
    }
    
    @Test
    public void testGetSetMeasure() {
    	productMeasureDto.setMeasure(new ProductMeasureDto(2L, "abv"));
    	assertEquals(new ProductMeasureDto(2L, "abv"), productMeasureDto.getMeasure());
    }
    
    @Test
    public void testGetSetValue() {
        productMeasureDto.setValue("100");
        assertEquals("100", productMeasureDto.getValue());
    }

}
