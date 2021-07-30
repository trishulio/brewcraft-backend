package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.dto.ProductMeasureValueDto;

public class ProductMeasureValueDtoTest {
    
    ProductMeasureValueDto productMeasureValueDto;
    
    @BeforeEach
    public void init() {
        productMeasureValueDto = new ProductMeasureValueDto();
    }
    
    @Test
    public void testConstructor() {
        productMeasureValueDto = new ProductMeasureValueDto(1L, new MeasureDto(2L, "abv", 1), "100");
        
        assertEquals(1L, productMeasureValueDto.getId());
        assertEquals(new MeasureDto(2L, "abv", 1), productMeasureValueDto.getMeasure());
        assertEquals("100", productMeasureValueDto.getValue());
    }
    
    @Test
    public void testGetSetId() {
        productMeasureValueDto.setId(1L);
        assertEquals(1L, productMeasureValueDto.getId());
    }
    
    @Test
    public void testGetSetMeasure() {
    	productMeasureValueDto.setMeasure(new MeasureDto(2L, "abv", 1));
    	assertEquals(new MeasureDto(2L, "abv", 1), productMeasureValueDto.getMeasure());
    }
    
    @Test
    public void testGetSetValue() {
        productMeasureValueDto.setValue("100");
        assertEquals("100", productMeasureValueDto.getValue());
    }

}
