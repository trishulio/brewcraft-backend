//package io.company.brewcraft.service.mapper;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalDateTime;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import io.company.brewcraft.dto.CategoryDto;
//import io.company.brewcraft.dto.ProductDto;
//import io.company.brewcraft.dto.ProductMeasuresDto;
//import io.company.brewcraft.model.ProductCategoryEntity;
//import io.company.brewcraft.model.ProductEntity;
//import io.company.brewcraft.model.ProductMeasuresEntity;
//import io.company.brewcraft.pojo.Product;
//import io.company.brewcraft.pojo.ProductMeasures;
//import io.company.brewcraft.pojo.Category;
//
//public class ProductMapperTest {
//
//    private ProductMapper productMapper;
//
//    @BeforeEach
//    public void init() {
//        productMapper = ProductMapper.INSTANCE;
//    }
//
//    @Test
//    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
//        ProductEntity productEntity = new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        Product product = productMapper.fromEntity(productEntity, new CycleAvoidingMappingContext());
//
//        assertEquals(new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1), product);
//    }
//
//    @Test
//    public void testFromDto_ReturnPojo_WhenDtoIsNotNull() {
//        ProductDto dto = new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L, null, null, null), null, null, new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), 1);
//        Product product = productMapper.fromDto(dto);
//        
//        assertEquals(new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), null, null, 1), product);
//    }
//
//    @Test
//    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
//        Product product = new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        ProductDto dto = productMapper.toDto(product);
//
//        assertEquals(new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L, null, null, null), null, null, new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), 1), dto);
//    }
//
//    @Test
//    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
//        Product product = new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        ProductEntity entity = productMapper.toEntity(product, new CycleAvoidingMappingContext());
//
//        assertEquals(new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1), entity);
//    }
//    
//    @Test
//    public void testBeforeDto_setsAllCategories() {
//        ProductDto productDto = new ProductDto(1L, "product1", null, null, null, null, null, null);
//        
//        Category rootCategory = new Category(1L, "root", null, null, null, null, null);
//        Category subcategory1 = new Category(2L, "subcategory1", rootCategory, null, null, null, null);
//        Category subcategory2 = new Category(3L, "subcategory2", subcategory1, null, null, null, null);
//
//        Product product = new Product(1L, "testProduct", "testDescription", subcategory2, new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
//        
//        productMapper.beforetoDto(productDto, product);
//
//        assertEquals(rootCategory.getId(), productDto.getProductClass().getId());
//        assertEquals(subcategory1.getId(), productDto.getType().getId());
//        assertEquals(subcategory2.getId(), productDto.getStyle().getId());
//    }
//    
// }
