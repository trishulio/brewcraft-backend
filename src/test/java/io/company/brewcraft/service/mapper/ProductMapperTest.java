package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.ProductMeasuresDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.ProductMeasures;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void init() {
        productMapper = ProductMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsEntity() {
        ProductDto dto = new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L, null, null, null), null, null, new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), 1);
        Product product = productMapper.fromDto(dto);
        
        assertEquals(new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ProductMeasures(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), null, null, null, 1), product);
    }
    
    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddProductDto dto = new AddProductDto("testProduct", "testDescription", 1L, new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0));
        Product product = productMapper.fromDto(dto);
        
        assertEquals(new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ProductMeasures(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), null, null, null, null), product);
    }
    
    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateProductDto dto = new UpdateProductDto("testProduct", "testDescription", 1L, new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), 1);
        Product product = productMapper.fromDto(dto);
        
        assertEquals(new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ProductMeasures(null, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), null, null, null, 1), product);
    }

    @Test
    public void testToDto_ReturnsDto() {
        Product product = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductDto dto = productMapper.toDto(product);

        assertEquals(new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L, null, null, null), null, null, new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), 1), dto);
    }
    
    @Test
    public void testBeforeDto_setsAllCategories() {
        ProductDto productDto = new ProductDto(1L, "product1", null, null, null, null, null, null);
        
        ProductCategory rootCategory = new ProductCategory(1L, "root", null, null, null, null, null);
        ProductCategory subcategory1 = new ProductCategory(2L, "subcategory1", rootCategory, null, null, null, null);
        ProductCategory subcategory2 = new ProductCategory(3L, "subcategory2", subcategory1, null, null, null, null);

        Product product = new Product(1L, "testProduct", "testDescription", subcategory2, new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        productMapper.beforetoDto(productDto, product);

        assertEquals(rootCategory.getId(), productDto.getProductClass().getId());
        assertEquals(subcategory1.getId(), productDto.getType().getId());
        assertEquals(subcategory2.getId(), productDto.getStyle().getId());
    }
    
 }
