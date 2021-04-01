package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.ProductMeasureDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasure;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.model.Product;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void init() {
        productMapper = ProductMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsEntity() {
        ProductDto dto = new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L, null, null, null), null, null, List.of(new ProductMeasureDto("abv", "100")), 1);
        Product product = productMapper.fromDto(dto);
        
        Product expectedProduct = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(null ,new ProductMeasure("abv"), "100", null)), null, null, null, 1);
        expectedProduct.getTargetMeasures().get(0).setProduct(expectedProduct);
        
        assertEquals(expectedProduct, product);
    }
    
    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddProductDto dto = new AddProductDto("testProduct", "testDescription", 1L, List.of(new ProductMeasureDto("abv", "100")));
        Product product = productMapper.fromDto(dto);
        
        Product expectedProduct = new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(null ,new ProductMeasure("abv"), "100", null)), null, null, null, null);
        expectedProduct.getTargetMeasures().get(0).setProduct(expectedProduct);
        
        assertEquals(expectedProduct, product);
    }
    
    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateProductDto dto = new UpdateProductDto("testProduct", "testDescription", 1L, List.of(new ProductMeasureDto("abv", "100")), 1);
        Product product = productMapper.fromDto(dto);
        
        Product expectedProduct = new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(null, new ProductMeasure("abv"), "100", null)), null, null, null, 1);
        expectedProduct.getTargetMeasures().get(0).setProduct(expectedProduct);
        
        assertEquals(expectedProduct, product);
    }

    @Test
    public void testToDto_ReturnsDto() {
        Product product = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductDto dto = productMapper.toDto(product);

        assertEquals(new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L, null, null, null), null, null, List.of(new ProductMeasureDto("abv", "100")), 1), dto);
    }
    
    @Test
    public void testBeforeDto_setsAllCategories() {
        ProductDto productDto = new ProductDto(1L, "product1", null, null, null, null, null, null);
        
        ProductCategory rootCategory = new ProductCategory(1L, "root", null, null, null, null, null);
        ProductCategory subcategory1 = new ProductCategory(2L, "subcategory1", rootCategory, null, null, null, null);
        ProductCategory subcategory2 = new ProductCategory(3L, "subcategory2", subcategory1, null, null, null, null);

        Product product = new Product(1L, "testProduct", "testDescription", subcategory2, List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        productMapper.beforetoDto(productDto, product);

        assertEquals(rootCategory.getId(), productDto.getProductClass().getId());
        assertEquals(subcategory1.getId(), productDto.getType().getId());
        assertEquals(subcategory2.getId(), productDto.getStyle().getId());
    }
    
 }
