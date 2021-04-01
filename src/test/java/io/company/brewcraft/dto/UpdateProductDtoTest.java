package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateProductDtoTest {

    private UpdateProductDto updateProductDto;

    @BeforeEach
    public void init() {
        updateProductDto = new UpdateProductDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "testName";
        String description = "testDesc";
        Long categoryId = 1L;
        List<ProductMeasureDto> targetMeasures = List.of(new ProductMeasureDto());
        Integer version = 1;

        UpdateProductDto productDto = new UpdateProductDto(name, description, categoryId, targetMeasures, version);
        
        assertEquals("testName", productDto.getName());
        assertEquals("testDesc", productDto.getDescription());
        assertEquals(1L, productDto.getCategoryId());
        assertEquals(List.of(new ProductMeasureDto()), productDto.getTargetMeasures());
        assertEquals(1, productDto.getVersion());        
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateProductDto.setName(name);
        assertEquals(name, updateProductDto.getName());
    }
    
    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        updateProductDto.setDescription(description);
        assertEquals(description, updateProductDto.getDescription());
    }
    
    
    @Test
    public void testGetSetCategoryId() {
        Long categoryId = 1L;
        updateProductDto.setCategoryId(categoryId);
        assertEquals(categoryId, updateProductDto.getCategoryId());
    }
    
    @Test
    public void testGetSetTargetMeasures() {
        List<ProductMeasureDto> targetMeasures = List.of(new ProductMeasureDto());
        updateProductDto.setTargetMeasures(targetMeasures);
        assertEquals(List.of(new ProductMeasureDto()), updateProductDto.getTargetMeasures());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateProductDto.setVersion(version);
        assertEquals(version, updateProductDto.getVersion());
    }
}
