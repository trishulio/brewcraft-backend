//package io.company.brewcraft.dto;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class AddProductDtoTest {
//
//    private AddProductDto addProductDto;
//
//    @BeforeEach
//    public void init() {
//        addProductDto = new AddProductDto();
//    }
//    
//    @Test
//    public void testConstructor() {
//        String name = "testName";
//        String description = "testDesc";
//        Long categoryId = 1L;
//        ProductMeasuresDto targetMeasures = new ProductMeasuresDto();
//
//        AddProductDto addProductDto = new AddProductDto(name, description, categoryId, targetMeasures);
//        
//        assertSame(name, addProductDto.getName());
//        assertSame(description, addProductDto.getDescription());
//        assertSame(categoryId, addProductDto.getCategoryId());
//        assertSame(targetMeasures, addProductDto.getTargetMeasures());
//    }
//
//    @Test
//    public void testGetSetName() {
//        String name = "testName";
//        addProductDto.setName(name);
//        assertSame(name, addProductDto.getName());
//    }
//    
//    @Test
//    public void testGetSetDescription() {
//        String description = "testDesc";
//        addProductDto.setDescription(description);
//        assertSame(description, addProductDto.getDescription());
//    }
//    
//    @Test
//    public void testGetSetCategoryId() {
//        Long categoryId = 1L;
//        addProductDto.setCategoryId(categoryId);
//        assertSame(categoryId, addProductDto.getCategoryId());
//    }
//    
//    @Test
//    public void testGetSetTargetMeasures() {
//        ProductMeasuresDto targetMeasures = new ProductMeasuresDto();
//        addProductDto.setTargetMeasures(targetMeasures);
//        assertSame(targetMeasures, addProductDto.getTargetMeasures());
//    }
//}
