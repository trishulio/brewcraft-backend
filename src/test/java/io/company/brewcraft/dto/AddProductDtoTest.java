package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddProductDtoTest {
    private AddProductDto addProductDto;

    @BeforeEach
    public void init() {
        addProductDto = new AddProductDto();
    }

    @Test
    public void testConstructor() throws Exception {
        String name = "testName";
        String description = "testDesc";
        Long categoryId = 1L;
        List<AddProductMeasureValueDto> targetMeasures = List.of(new AddProductMeasureValueDto());
        URI imageSrc = new URI("http://www.test.com");

        AddProductDto addProductDto = new AddProductDto(name, description, categoryId, targetMeasures, imageSrc);

        assertEquals("testName", addProductDto.getName());
        assertEquals("testDesc", addProductDto.getDescription());
        assertEquals(1L, addProductDto.getCategoryId());
        assertEquals(List.of(new AddProductMeasureValueDto()), addProductDto.getTargetMeasures());
        assertEquals(imageSrc, addProductDto.getImageSrc());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        addProductDto.setName(name);
        assertEquals("testName", addProductDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        addProductDto.setDescription(description);
        assertEquals("testDesc", addProductDto.getDescription());
    }

    @Test
    public void testGetSetCategoryId() {
        Long categoryId = 1L;
        addProductDto.setCategoryId(categoryId);
        assertEquals(1L, addProductDto.getCategoryId());
    }

    @Test
    public void testGetSetTargetMeasures() {
        List<AddProductMeasureValueDto> targetMeasures = List.of(new AddProductMeasureValueDto());
        addProductDto.setTargetMeasures(targetMeasures);
        assertEquals(List.of(new AddProductMeasureValueDto()), addProductDto.getTargetMeasures());
    }

    @Test
    public void testGetSetImageSrc() throws Exception {
        addProductDto.setImageSrc(new URI("http://www.test.com"));
        assertEquals(new URI("http://www.test.com"), addProductDto.getImageSrc());
    }
}
