//package io.company.brewcraft.dto;
//
//import static org.junit.jupiter.api.Assertions.assertSame;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class ProductDtoTest {
//
//    private ProductDto productDto;
//
//    @BeforeEach
//    public void init() {
//        productDto = new ProductDto();
//    }
//    
//    @Test
//    public void testConstructor() {
//        Long id = 1L;
//        String name = "testName";
//        String description = "testDesc";
//        CategoryDto productClass = new CategoryDto();
//        CategoryDto type = new CategoryDto();
//        CategoryDto style = new CategoryDto();
//        ProductMeasuresDto targetMeasures = new ProductMeasuresDto();
//        Integer version = 1;
//
//        ProductDto productDto = new ProductDto(id, name, description, productClass, type, style, targetMeasures, version);
//        
//        assertSame(id, productDto.getId());
//        assertSame(name, productDto.getName());
//        assertSame(description, productDto.getDescription());
//        assertSame(productClass, productDto.getProductClass());
//        assertSame(type, productDto.getType());
//        assertSame(style, productDto.getStyle());
//        assertSame(targetMeasures, productDto.getTargetMeasures());
//        assertSame(version, productDto.getVersion());        
//    }
//    
//    @Test
//    public void testGetSetId() {
//        Long id = 1L;
//        productDto.setId(id);
//        assertSame(id, productDto.getId());
//    }
//
//    @Test
//    public void testGetSetName() {
//        String name = "testName";
//        productDto.setName(name);
//        assertSame(name, productDto.getName());
//    }
//    
//    @Test
//    public void testGetSetDescription() {
//        String description = "testDesc";
//        productDto.setDescription(description);
//        assertSame(description, productDto.getDescription());
//    }
//    
//    @Test
//    public void testGetSetProductClass() {
//        CategoryDto productClass = new CategoryDto();
//        productDto.setProductClass(productClass);
//        assertSame(productClass, productDto.getProductClass());
//    }
//    
//    @Test
//    public void testGetSetType() {
//        CategoryDto type = new CategoryDto();
//        productDto.setType(type);
//        assertSame(type, productDto.getType());
//    }
//    
//    @Test
//    public void testGetSetStyle() {
//        CategoryDto style = new CategoryDto();
//        productDto.setStyle(style);
//        assertSame(style, productDto.getStyle());
//    }
//    
//    @Test
//    public void testGetSetTargetMeasures() {
//        ProductMeasuresDto targetMeasures = new ProductMeasuresDto();
//        productDto.setTargetMeasures(targetMeasures);
//        assertSame(targetMeasures, productDto.getTargetMeasures());
//    }
//    
//    @Test
//    public void testGetSetVersion() {
//        Integer version = 1;
//        productDto.setVersion(version);
//        assertSame(version, productDto.getVersion());
//    }
//}
