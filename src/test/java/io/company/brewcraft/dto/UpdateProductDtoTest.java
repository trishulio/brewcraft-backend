//package io.company.brewcraft.dto;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class UpdateProductDtoTest {
//
//    private UpdateProductDto updateProductDto;
//
//    @BeforeEach
//    public void init() {
//        updateProductDto = new UpdateProductDto();
//    }
//    
//    @Test
//    public void testConstructor() {
//        String name = "testName";
//        String description = "testDesc";
//        Long categoryId = 1L;
//        ProductMeasuresDto targetMeasures = new ProductMeasuresDto();
//        Integer version = 1;
//
//        UpdateProductDto productDto = new UpdateProductDto(name, description, categoryId, targetMeasures, version);
//        
//        assertSame(name, productDto.getName());
//        assertSame(description, productDto.getDescription());
//        assertSame(categoryId, productDto.getCategoryId());
//        assertSame(targetMeasures, productDto.getTargetMeasures());
//        assertSame(version, productDto.getVersion());        
//    }
//
//    @Test
//    public void testGetSetName() {
//        String name = "testName";
//        updateProductDto.setName(name);
//        assertSame(name, updateProductDto.getName());
//    }
//    
//    @Test
//    public void testGetSetDescription() {
//        String description = "testDesc";
//        updateProductDto.setDescription(description);
//        assertSame(description, updateProductDto.getDescription());
//    }
//    
//    
//    @Test
//    public void testGetSetCategoryId() {
//        Long categoryId = 1L;
//        updateProductDto.setCategoryId(categoryId);
//        assertSame(categoryId, updateProductDto.getCategoryId());
//    }
//    
//    @Test
//    public void testGetSetTargetMeasures() {
//        ProductMeasuresDto targetMeasures = new ProductMeasuresDto();
//        updateProductDto.setTargetMeasures(targetMeasures);
//        assertSame(targetMeasures, updateProductDto.getTargetMeasures());
//    }
//    
//    @Test
//    public void testGetSetVersion() {
//        Integer version = 1;
//        updateProductDto.setVersion(version);
//        assertSame(version, updateProductDto.getVersion());
//    }
//}
