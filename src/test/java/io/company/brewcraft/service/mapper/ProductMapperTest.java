package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.AddProductMeasureValueDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.MeasureDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.ProductMeasureValueDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasureValue;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void init() {
        productMapper = ProductMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsEntity() throws Exception {
        ProductDto dto = new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L), null, null, List.of(new ProductMeasureValueDto(1L, new MeasureDto(1L, "abv", 1), new BigDecimal("100"))), new URI("http://www.test.com"), 1);
        Product product = productMapper.fromDto(dto);

        Product expectedProduct = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L), List.of(new ProductMeasureValue(1L ,new Measure(1L, "abv", null, null, 1), new BigDecimal("100"), null)), new URI("http://www.test.com"), null, null, null, 1);
        expectedProduct.getTargetMeasures().get(0).setProduct(expectedProduct);

        assertEquals(expectedProduct, product);
    }

    @Test
    public void testFromAddDto_ReturnsEntity() throws Exception {
        AddProductDto dto = new AddProductDto("testProduct", "testDescription", 1L, List.of(new AddProductMeasureValueDto(1L, new BigDecimal("100"))), new URI("http://www.test.com"));
        Product product = productMapper.fromDto(dto);

        Product expectedProduct = new Product(null, "testProduct", "testDescription", new ProductCategory(1L), List.of(new ProductMeasureValue(null ,new Measure(1L), new BigDecimal("100"), null)), new URI("http://www.test.com"), null, null, null, null);
        expectedProduct.getTargetMeasures().get(0).setProduct(expectedProduct);

        assertEquals(expectedProduct, product);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() throws Exception {
        UpdateProductDto dto = new UpdateProductDto("testProduct", "testDescription", 1L, List.of(new AddProductMeasureValueDto(1L, new BigDecimal("100"))), new URI("http://www.test.com"), 1);
        Product product = productMapper.fromDto(dto);

        Product expectedProduct = new Product(null, "testProduct", "testDescription", new ProductCategory(1L), List.of(new ProductMeasureValue(null, new Measure(1L), new BigDecimal("100"), null)), new URI("http://www.test.com"), null, null, null, 1);
        expectedProduct.getTargetMeasures().get(0).setProduct(expectedProduct);

        assertEquals(expectedProduct, product);
    }

    @Test
    public void testToDto_ReturnsDto() throws Exception {
        Product product = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L), List.of(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product())), new URI("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductDto dto = productMapper.toDto(product);

        assertEquals(new ProductDto(1L, "testProduct", "testDescription", new CategoryDto(1L), null, null, List.of(new ProductMeasureValueDto(1L, new MeasureDto(1L, "abv", 1), new BigDecimal("100"))), new URI("http://www.test.com"), 1), dto);
    }

    @Test
    public void testBeforeDto_setsAllCategories() throws Exception {
        ProductDto productDto = new ProductDto(1L, "product1", null, null, null, null, null, null, null);

        ProductCategory rootCategory = new ProductCategory(1L, "root", null, null, null, null, null);
        ProductCategory subcategory1 = new ProductCategory(2L, "subcategory1", rootCategory, null, null, null, null);
        ProductCategory subcategory2 = new ProductCategory(3L, "subcategory2", subcategory1, null, null, null, null);

        Product product = new Product(1L, "testProduct", "testDescription", subcategory2, List.of(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product())), new URI("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        productMapper.beforetoDto(productDto, product);

        assertEquals(rootCategory.getId(), productDto.getProductClass().getId());
        assertEquals(subcategory1.getId(), productDto.getType().getId());
        assertEquals(subcategory2.getId(), productDto.getStyle().getId());
    }
 }
