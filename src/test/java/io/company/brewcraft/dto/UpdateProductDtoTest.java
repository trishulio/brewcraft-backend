package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
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
    public void testConstructor() throws Exception {
        String name = "testName";
        String description = "testDesc";
        Long categoryId = 1L;
        List<AddProductMeasureValueDto> targetMeasures = List.of(new AddProductMeasureValueDto());
        URI imageSrc = new URI("http://www.test.com");
        Integer version = 1;

        UpdateProductDto productDto = new UpdateProductDto(name, description, categoryId, targetMeasures, imageSrc, version);

        assertEquals("testName", productDto.getName());
        assertEquals("testDesc", productDto.getDescription());
        assertEquals(1L, productDto.getCategoryId());
        assertEquals(List.of(new AddProductMeasureValueDto()), productDto.getTargetMeasures());
        assertEquals(imageSrc, productDto.getImageSrc());
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
        List<AddProductMeasureValueDto> targetMeasures = List.of(new AddProductMeasureValueDto());
        updateProductDto.setTargetMeasures(targetMeasures);
        assertEquals(List.of(new AddProductMeasureValueDto()), updateProductDto.getTargetMeasures());
    }

    @Test
    public void testGetSetImageSrc() throws Exception {
        updateProductDto.setImageSrc(new URI("http://www.test.com"));
        assertEquals(new URI("http://www.test.com"), updateProductDto.getImageSrc());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateProductDto.setVersion(version);
        assertEquals(version, updateProductDto.getVersion());
    }
}
