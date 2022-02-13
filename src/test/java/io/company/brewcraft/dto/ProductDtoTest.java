package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDtoTest {

    private ProductDto productDto;

    @BeforeEach
    public void init() {
        productDto = new ProductDto();
    }

    @Test
    public void testConstructor() throws Exception {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        CategoryDto productClass = new CategoryDto();
        CategoryDto type = new CategoryDto();
        CategoryDto style = new CategoryDto();
        List<ProductMeasureValueDto> targetMeasures = List.of(new ProductMeasureValueDto());
        URI imageSrc = new URI("http://www.test.com");
        Integer version = 1;

        ProductDto productDto = new ProductDto(id, name, description, productClass, type, style, targetMeasures, imageSrc, version);

        assertEquals(id, productDto.getId());
        assertEquals("testName", productDto.getName());
        assertEquals("testDesc", productDto.getDescription());
        assertEquals(new CategoryDto(), productDto.getProductClass());
        assertEquals(new CategoryDto(), productDto.getType());
        assertEquals(new CategoryDto(), productDto.getStyle());
        assertEquals(List.of(new ProductMeasureValueDto()), productDto.getTargetMeasures());
        assertEquals(imageSrc, productDto.getImageSrc());
        assertEquals(1, productDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        productDto.setId(id);
        assertEquals(id, productDto.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        productDto.setName(name);
        assertEquals(name, productDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        productDto.setDescription(description);
        assertEquals(description, productDto.getDescription());
    }

    @Test
    public void testGetSetProductClass() {
        CategoryDto productClass = new CategoryDto();
        productDto.setProductClass(productClass);
        assertEquals(productClass, productDto.getProductClass());
    }

    @Test
    public void testGetSetType() {
        CategoryDto type = new CategoryDto();
        productDto.setType(type);
        assertEquals(type, productDto.getType());
    }

    @Test
    public void testGetSetStyle() {
        CategoryDto style = new CategoryDto();
        productDto.setStyle(style);
        assertEquals(style, productDto.getStyle());
    }

    @Test
    public void testGetSetTargetMeasures() {
        List<ProductMeasureValueDto> targetMeasures = List.of(new ProductMeasureValueDto());
        productDto.setTargetMeasures(targetMeasures);
        assertEquals(List.of(new ProductMeasureValueDto()), productDto.getTargetMeasures());
    }

    @Test
    public void testGetSetImageSrc() throws Exception {
        productDto.setImageSrc(new URI("http://www.test.com"));
        assertEquals(new URI("http://www.test.com"), productDto.getImageSrc());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        productDto.setVersion(version);
        assertEquals(version, productDto.getVersion());
    }
}
