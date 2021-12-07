package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.AddProductMeasureValueDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class ProductControllerTest {

   private ProductController productController;

   private ProductService productService;

   @BeforeEach
   public void init() {
       productService = mock(ProductService.class);

       productController = new ProductController(productService, new AttributeFilter());
   }

   @Test
   public void testGetProducts() throws Exception {
       ProductCategory productClass = new ProductCategory(1L, "testClass", null, null, null, null, null);
       ProductCategory type = new ProductCategory(2L, "testType", productClass, null, null, null, null);
       ProductCategory style = new ProductCategory(3L, "testStyle", type, null, null, null, null);
       List<ProductMeasureValue> targetMeasures = List.of(new ProductMeasureValue(1L,new Measure(2L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));

       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, new URL("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), null, 1);

       List<Product> productList = List.of(product);
       Page<Product> mPage = mock(Page.class);
       doReturn(productList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(productService).getProducts(
           Set.of(1L),
           Set.of(2L),
           Set.of("Beer"),
           1,
           10,
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<ProductDto> dto = productController.getProducts(
               Set.of(1L),
               Set.of(2L),
               Set.of("Beer"),
               new TreeSet<>(List.of("id")),
               true,
               1,
               10
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       ProductDto productDto = dto.getContent().get(0);

       assertEquals(product.getId(), productDto.getId());
       assertEquals(product.getName(), productDto.getName());
       assertEquals(product.getDescription(), productDto.getDescription());

       assertEquals(product.getCategory().getId(), productDto.getStyle().getId());
       assertEquals(product.getCategory().getName(), productDto.getStyle().getName());

       assertEquals(product.getCategory().getParentCategory().getId(), productDto.getType().getId());
       assertEquals(product.getCategory().getParentCategory().getName(), productDto.getType().getName());

       assertEquals(product.getCategory().getParentCategory().getParentCategory().getId(), productDto.getProductClass().getId());
       assertEquals(product.getCategory().getParentCategory().getParentCategory().getName(), productDto.getProductClass().getName());
       assertEquals(null, productDto.getProductClass().getParentCategoryId());

       assertEquals(product.getTargetMeasures().size(), productDto.getTargetMeasures().size());
       assertEquals(product.getTargetMeasures().get(0).getMeasure().getName(), productDto.getTargetMeasures().get(0).getMeasure().getName());
       assertEquals(product.getTargetMeasures().get(0).getValue(), productDto.getTargetMeasures().get(0).getValue());

       assertEquals(product.getImageSrc(), productDto.getImageSrc());
       assertEquals(product.getVersion(), productDto.getVersion());
   }

   @Test
   public void testGetProduct() throws Exception {
       ProductCategory productClass = new ProductCategory(1L, "testClass", null, null, null, null, null);
       ProductCategory type = new ProductCategory(2L, "testType", productClass, null, null, null, null);
       ProductCategory style = new ProductCategory(3L, "testStyle", type, null, null, null, null);
       List<ProductMeasureValue> targetMeasures = List.of(new ProductMeasureValue(1L,new Measure(2L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));

       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, new URL("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

       doReturn(product).when(productService).getProduct(1L);

       ProductDto productDto = productController.getProduct(1L);

       assertEquals(product.getId(), productDto.getId());
       assertEquals(product.getName(), productDto.getName());
       assertEquals(product.getDescription(), productDto.getDescription());

       assertEquals(product.getCategory().getId(), productDto.getStyle().getId());
       assertEquals(product.getCategory().getName(), productDto.getStyle().getName());

       assertEquals(product.getCategory().getParentCategory().getId(), productDto.getType().getId());
       assertEquals(product.getCategory().getParentCategory().getName(), productDto.getType().getName());

       assertEquals(product.getCategory().getParentCategory().getParentCategory().getId(), productDto.getProductClass().getId());
       assertEquals(product.getCategory().getParentCategory().getParentCategory().getName(), productDto.getProductClass().getName());
       assertEquals(null, productDto.getProductClass().getParentCategoryId());

       assertEquals(product.getTargetMeasures().size(), productDto.getTargetMeasures().size());
       assertEquals(product.getTargetMeasures().get(0).getMeasure().getName(), productDto.getTargetMeasures().get(0).getMeasure().getName());
       assertEquals(product.getTargetMeasures().get(0).getValue(), productDto.getTargetMeasures().get(0).getValue());

       assertEquals(product.getImageSrc(), productDto.getImageSrc());
       assertEquals(product.getVersion(), productDto.getVersion());
   }

   @Test
   public void testGetProduct_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(productService.getProduct(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> productController.getProduct(1L), "Product not found with id: 1");
   }

   @Test
   public void testAddProduct() throws Exception {
       List<AddProductMeasureValueDto> targetMeasuresDto = List.of(new AddProductMeasureValueDto(1L, new BigDecimal("100")));
       AddProductDto addProductDto = new AddProductDto("testProduct", "testDescription", 2L, targetMeasuresDto, new URL("http://www.test.com"));

       ProductCategory productClass = new ProductCategory(1L, "testClass", null, null, null, null, null);
       ProductCategory type = new ProductCategory(2L, "testType", productClass, null, null, null, null);
       ProductCategory style = new ProductCategory(3L, "testStyle", type, null, null, null, null);
       List<ProductMeasureValue> targetMeasures = List.of(new ProductMeasureValue(10L, new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));

       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, new URL("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

       ArgumentCaptor<Product> addProductCaptor = ArgumentCaptor.forClass(Product.class);

       doReturn(product).when(productService).addProduct(addProductCaptor.capture(), eq(addProductDto.getCategoryId()));

       ProductDto productDto = productController.addProduct(addProductDto);

       //Assert added product
       assertEquals(null, addProductCaptor.getValue().getId());
       assertEquals(addProductDto.getName(), addProductCaptor.getValue().getName());
       assertEquals(addProductDto.getDescription(), addProductCaptor.getValue().getDescription());
       assertEquals(addProductDto.getCategoryId(), addProductCaptor.getValue().getCategory().getId());

       assertEquals(addProductDto.getTargetMeasures().size(), addProductCaptor.getValue().getTargetMeasures().size());
       assertEquals(addProductDto.getTargetMeasures().get(0).getMeasureId(), addProductCaptor.getValue().getTargetMeasures().get(0).getMeasure().getId());
       assertEquals(addProductDto.getTargetMeasures().get(0).getValue(), addProductCaptor.getValue().getTargetMeasures().get(0).getValue());

       assertEquals(addProductDto.getImageSrc(), addProductCaptor.getValue().getImageSrc());

       //Assert returned product
       assertEquals(product.getId(), productDto.getId());
       assertEquals(product.getName(), productDto.getName());
       assertEquals(product.getDescription(), productDto.getDescription());

       assertEquals(product.getCategory().getId(), productDto.getStyle().getId());
       assertEquals(product.getCategory().getName(), productDto.getStyle().getName());

       assertEquals(product.getCategory().getParentCategory().getId(), productDto.getType().getId());
       assertEquals(product.getCategory().getParentCategory().getName(), productDto.getType().getName());

       assertEquals(product.getCategory().getParentCategory().getParentCategory().getId(), productDto.getProductClass().getId());
       assertEquals(product.getCategory().getParentCategory().getParentCategory().getName(), productDto.getProductClass().getName());
       assertEquals(null, productDto.getProductClass().getParentCategoryId());

       assertEquals(product.getTargetMeasures().size(), productDto.getTargetMeasures().size());
       assertEquals(product.getTargetMeasures().get(0).getMeasure().getId(), productDto.getTargetMeasures().get(0).getMeasure().getId());
       assertEquals(product.getTargetMeasures().get(0).getValue(), productDto.getTargetMeasures().get(0).getValue());

       assertEquals(product.getImageSrc(), productDto.getImageSrc());
       assertEquals(product.getVersion(), productDto.getVersion());
   }

   @Test
   public void testPutProduct() throws Exception {
       List<AddProductMeasureValueDto> targetMeasuresDto = List.of(new AddProductMeasureValueDto(1L, new BigDecimal("100")));
       UpdateProductDto updateProductDto = new UpdateProductDto("testProduct", "testDescription", 2L, targetMeasuresDto, new URL("http://www.test.com"), 1);

       ProductCategory productClass = new ProductCategory(1L, "testClass", null, null, null, null, null);
       ProductCategory type = new ProductCategory(2L, "testType", productClass, null, null, null, null);
       ProductCategory style = new ProductCategory(3L, "testStyle", type, null, null, null, null);
       List<ProductMeasureValue> targetMeasures = List.of(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));

       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, new URL("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

       ArgumentCaptor<Product> putProductCaptor = ArgumentCaptor.forClass(Product.class);

       doReturn(product).when(productService).putProduct(eq(1L), putProductCaptor.capture(), eq(updateProductDto.getCategoryId()));

       ProductDto productDto = productController.putProduct(updateProductDto, 1L);

       //Assert put product
       assertEquals(null, putProductCaptor.getValue().getId());
       assertEquals(updateProductDto.getName(), putProductCaptor.getValue().getName());
       assertEquals(updateProductDto.getDescription(), putProductCaptor.getValue().getDescription());
       assertEquals(updateProductDto.getCategoryId(), putProductCaptor.getValue().getCategory().getId());

       assertEquals(updateProductDto.getTargetMeasures().size(), putProductCaptor.getValue().getTargetMeasures().size());
       assertEquals(updateProductDto.getTargetMeasures().get(0).getMeasureId(), putProductCaptor.getValue().getTargetMeasures().get(0).getMeasure().getId());
       assertEquals(updateProductDto.getTargetMeasures().get(0).getValue(), putProductCaptor.getValue().getTargetMeasures().get(0).getValue());

       assertEquals(updateProductDto.getImageSrc(), putProductCaptor.getValue().getImageSrc());
       assertEquals(updateProductDto.getVersion(), putProductCaptor.getValue().getVersion());

       //Assert returned product
       assertEquals(product.getId(), productDto.getId());
       assertEquals(product.getName(), productDto.getName());
       assertEquals(product.getDescription(), productDto.getDescription());

       assertEquals(product.getCategory().getId(), productDto.getStyle().getId());
       assertEquals(product.getCategory().getName(), productDto.getStyle().getName());

       assertEquals(product.getCategory().getParentCategory().getId(), productDto.getType().getId());
       assertEquals(product.getCategory().getParentCategory().getName(), productDto.getType().getName());

       assertEquals(product.getCategory().getParentCategory().getParentCategory().getId(), productDto.getProductClass().getId());
       assertEquals(product.getCategory().getParentCategory().getParentCategory().getName(), productDto.getProductClass().getName());
       assertEquals(null, productDto.getProductClass().getParentCategoryId());

       assertEquals(product.getTargetMeasures().size(), productDto.getTargetMeasures().size());
       assertEquals(product.getTargetMeasures().get(0).getMeasure().getName(), productDto.getTargetMeasures().get(0).getMeasure().getName());
       assertEquals(product.getTargetMeasures().get(0).getValue(), productDto.getTargetMeasures().get(0).getValue());

       assertEquals(product.getImageSrc(), productDto.getImageSrc());
       assertEquals(product.getVersion(), productDto.getVersion());
   }

   @Test
   public void testPatchProduct() throws Exception {
       List<AddProductMeasureValueDto> targetMeasuresDto = List.of(new AddProductMeasureValueDto(1L, new BigDecimal("100")));
       UpdateProductDto updateProductDto = new UpdateProductDto("testProduct", "testDescription", 2L, targetMeasuresDto, new URL("http://www.test.com"), 1);

       ProductCategory productClass = new ProductCategory(1L, "testClass", null, null, null, null, null);
       ProductCategory type = new ProductCategory(2L, "testType", productClass, null, null, null, null);
       ProductCategory style = new ProductCategory(3L, "testStyle", type, null, null, null, null);
       List<ProductMeasureValue> targetMeasures = List.of(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));

       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, new URL("http://www.test.com"), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

       ArgumentCaptor<Product> patchProductCaptor = ArgumentCaptor.forClass(Product.class);

       doReturn(product).when(productService).patchProduct(eq(1L), patchProductCaptor.capture(), eq(updateProductDto.getCategoryId()));

       ProductDto productDto = productController.patchProduct(updateProductDto, 1L);

       //Assert patched product
       assertEquals(null, patchProductCaptor.getValue().getId());
       assertEquals(updateProductDto.getName(), patchProductCaptor.getValue().getName());
       assertEquals(updateProductDto.getDescription(), patchProductCaptor.getValue().getDescription());
       assertEquals(updateProductDto.getCategoryId(), patchProductCaptor.getValue().getCategory().getId());

       assertEquals(updateProductDto.getTargetMeasures().size(), patchProductCaptor.getValue().getTargetMeasures().size());
       assertEquals(updateProductDto.getTargetMeasures().get(0).getMeasureId(), patchProductCaptor.getValue().getTargetMeasures().get(0).getMeasure().getId());
       assertEquals(updateProductDto.getTargetMeasures().get(0).getValue(), patchProductCaptor.getValue().getTargetMeasures().get(0).getValue());

       assertEquals(updateProductDto.getImageSrc(), patchProductCaptor.getValue().getImageSrc());
       assertEquals(updateProductDto.getVersion(), patchProductCaptor.getValue().getVersion());

       //Assert returned product
       assertEquals(product.getId(), productDto.getId());
       assertEquals(product.getName(), productDto.getName());
       assertEquals(product.getDescription(), productDto.getDescription());

       assertEquals(product.getCategory().getId(), productDto.getStyle().getId());
       assertEquals(product.getCategory().getName(), productDto.getStyle().getName());

       assertEquals(product.getCategory().getParentCategory().getId(), productDto.getType().getId());
       assertEquals(product.getCategory().getParentCategory().getName(), productDto.getType().getName());

       assertEquals(product.getCategory().getParentCategory().getParentCategory().getId(), productDto.getProductClass().getId());
       assertEquals(product.getCategory().getParentCategory().getParentCategory().getName(), productDto.getProductClass().getName());
       assertEquals(null, productDto.getProductClass().getParentCategoryId());

       assertEquals(product.getTargetMeasures().size(), productDto.getTargetMeasures().size());
       assertEquals(product.getTargetMeasures().get(0).getMeasure().getName(), productDto.getTargetMeasures().get(0).getMeasure().getName());
       assertEquals(product.getTargetMeasures().get(0).getValue(), productDto.getTargetMeasures().get(0).getValue());

       assertEquals(product.getImageSrc(), productDto.getImageSrc());
       assertEquals(product.getVersion(), productDto.getVersion());
   }

   @Test
   public void testDeleteProduct() {
       productController.deleteProduct(1L);

       verify(productService, times(1)).deleteProduct(1L);
   }
}