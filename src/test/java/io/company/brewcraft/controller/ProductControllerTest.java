package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddProductDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.dto.ProductMeasuresDto;
import io.company.brewcraft.dto.UpdateProductDto;
import io.company.brewcraft.pojo.Product;
import io.company.brewcraft.pojo.ProductMeasures;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@SuppressWarnings("unchecked")
public class ProductControllerTest {

   private ProductController productController;

   private ProductService productService;

   @BeforeEach
   public void init() {
       productService = mock(ProductService.class);

       productController = new ProductController(productService);
   }

   @Test
   public void testGetProducts() {
       Category productClass = new Category(1L, "testClass", null, null, null, null, null);
       Category type = new Category(2L, "testType", productClass, null, null, null, null);
       Category style = new Category(3L, "testStyle", type, null, null, null, null);
       ProductMeasures targetMeasures = new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
       
       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), null, 1);
   
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
           Set.of("id"),
           true
       );

       PageDto<ProductDto> dto = productController.getProducts(
               Set.of(1L),
               Set.of(2L),
               Set.of("Beer"),
               1,
               10,
               Set.of("id"),
               true
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

       assertEquals(product.getTargetMeasures().getAbv(), productDto.getTargetMeasures().getAbv());
       assertEquals(product.getTargetMeasures().getIbu(), productDto.getTargetMeasures().getIbu());
       assertEquals(product.getTargetMeasures().getPh(), productDto.getTargetMeasures().getPh());
       assertEquals(product.getTargetMeasures().getMashTemperature(), productDto.getTargetMeasures().getMashTemperature());
       assertEquals(product.getTargetMeasures().getGravity(), productDto.getTargetMeasures().getGravity());
       assertEquals(product.getTargetMeasures().getYield(), productDto.getTargetMeasures().getYield());
       assertEquals(product.getTargetMeasures().getBrewhouseDuration(), productDto.getTargetMeasures().getBrewhouseDuration());
       assertEquals(product.getTargetMeasures().getFermentationDays(), productDto.getTargetMeasures().getFermentationDays());
       assertEquals(product.getTargetMeasures().getConditioningDays(), productDto.getTargetMeasures().getConditioningDays());
       
       assertEquals(product.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testGetProduct() {
       Category productClass = new Category(1L, "testClass", null, null, null, null, null);
       Category type = new Category(2L, "testType", productClass, null, null, null, null);
       Category style = new Category(3L, "testStyle", type, null, null, null, null);
       ProductMeasures targetMeasures = new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
       
       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
   
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

       assertEquals(product.getTargetMeasures().getAbv(), productDto.getTargetMeasures().getAbv());
       assertEquals(product.getTargetMeasures().getIbu(), productDto.getTargetMeasures().getIbu());
       assertEquals(product.getTargetMeasures().getPh(), productDto.getTargetMeasures().getPh());
       assertEquals(product.getTargetMeasures().getMashTemperature(), productDto.getTargetMeasures().getMashTemperature());
       assertEquals(product.getTargetMeasures().getGravity(), productDto.getTargetMeasures().getGravity());
       assertEquals(product.getTargetMeasures().getYield(), productDto.getTargetMeasures().getYield());
       assertEquals(product.getTargetMeasures().getBrewhouseDuration(), productDto.getTargetMeasures().getBrewhouseDuration());
       assertEquals(product.getTargetMeasures().getFermentationDays(), productDto.getTargetMeasures().getFermentationDays());
       assertEquals(product.getTargetMeasures().getConditioningDays(), productDto.getTargetMeasures().getConditioningDays());
       
       assertEquals(product.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testGetProduct_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(productService.getProduct(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> productController.getProduct(1L), "Product not found with id: 1");
   }

   @Test
   public void testAddProduct() {
       ProductMeasuresDto targetMeasuresDto = new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);  
       AddProductDto addProductDto = new AddProductDto("testProduct", "testDescription", 2L, targetMeasuresDto);
       
       Category productClass = new Category(1L, "testClass", null, null, null, null, null);
       Category type = new Category(2L, "testType", productClass, null, null, null, null);
       Category style = new Category(3L, "testStyle", type, null, null, null, null);
       ProductMeasures targetMeasures = new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
       
       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
   
       ArgumentCaptor<Product> addProductCaptor = ArgumentCaptor.forClass(Product.class);
       
       doReturn(product).when(productService).addProduct(addProductCaptor.capture(), eq(addProductDto.getCategoryId()));

       ProductDto productDto = productController.addProduct(addProductDto);
       
       //Assert added product
       assertEquals(null, addProductCaptor.getValue().getId());
       assertEquals(addProductDto.getName(), addProductCaptor.getValue().getName());
       assertEquals(addProductDto.getDescription(), addProductCaptor.getValue().getDescription());
       assertEquals(addProductDto.getCategoryId(), addProductCaptor.getValue().getCategory().getId());

       assertEquals(addProductDto.getTargetMeasures().getAbv(), addProductCaptor.getValue().getTargetMeasures().getAbv());
       assertEquals(addProductDto.getTargetMeasures().getIbu(), addProductCaptor.getValue().getTargetMeasures().getIbu());
       assertEquals(addProductDto.getTargetMeasures().getPh(), addProductCaptor.getValue().getTargetMeasures().getPh());
       assertEquals(addProductDto.getTargetMeasures().getMashTemperature(), addProductCaptor.getValue().getTargetMeasures().getMashTemperature());
       assertEquals(addProductDto.getTargetMeasures().getGravity(), addProductCaptor.getValue().getTargetMeasures().getGravity());
       assertEquals(addProductDto.getTargetMeasures().getYield(), addProductCaptor.getValue().getTargetMeasures().getYield());
       assertEquals(addProductDto.getTargetMeasures().getBrewhouseDuration(), addProductCaptor.getValue().getTargetMeasures().getBrewhouseDuration());
       assertEquals(addProductDto.getTargetMeasures().getFermentationDays(), addProductCaptor.getValue().getTargetMeasures().getFermentationDays());
       assertEquals(addProductDto.getTargetMeasures().getConditioningDays(), addProductCaptor.getValue().getTargetMeasures().getConditioningDays());
              
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

       assertEquals(product.getTargetMeasures().getAbv(), productDto.getTargetMeasures().getAbv());
       assertEquals(product.getTargetMeasures().getIbu(), productDto.getTargetMeasures().getIbu());
       assertEquals(product.getTargetMeasures().getPh(), productDto.getTargetMeasures().getPh());
       assertEquals(product.getTargetMeasures().getMashTemperature(), productDto.getTargetMeasures().getMashTemperature());
       assertEquals(product.getTargetMeasures().getGravity(), productDto.getTargetMeasures().getGravity());
       assertEquals(product.getTargetMeasures().getYield(), productDto.getTargetMeasures().getYield());
       assertEquals(product.getTargetMeasures().getBrewhouseDuration(), productDto.getTargetMeasures().getBrewhouseDuration());
       assertEquals(product.getTargetMeasures().getFermentationDays(), productDto.getTargetMeasures().getFermentationDays());
       assertEquals(product.getTargetMeasures().getConditioningDays(), productDto.getTargetMeasures().getConditioningDays());
       
       assertEquals(product.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testPutProduct() {
       ProductMeasuresDto targetMeasuresDto = new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);  
       UpdateProductDto updateProductDto = new UpdateProductDto("testProduct", "testDescription", 2L, targetMeasuresDto, 1);
              
       Category productClass = new Category(1L, "testClass", null, null, null, null, null);
       Category type = new Category(2L, "testType", productClass, null, null, null, null);
       Category style = new Category(3L, "testStyle", type, null, null, null, null);
       ProductMeasures targetMeasures = new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
       
       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
   
       ArgumentCaptor<Product> putProductCaptor = ArgumentCaptor.forClass(Product.class);
       
       doReturn(product).when(productService).putProduct(eq(1L), putProductCaptor.capture(), eq(updateProductDto.getCategoryId()));

       ProductDto productDto = productController.putProduct(updateProductDto, 1L);
       
       //Assert put product
       assertEquals(null, putProductCaptor.getValue().getId());
       assertEquals(updateProductDto.getName(), putProductCaptor.getValue().getName());
       assertEquals(updateProductDto.getDescription(), putProductCaptor.getValue().getDescription());
       assertEquals(updateProductDto.getCategoryId(), putProductCaptor.getValue().getCategory().getId());

       assertEquals(updateProductDto.getTargetMeasures().getAbv(), putProductCaptor.getValue().getTargetMeasures().getAbv());
       assertEquals(updateProductDto.getTargetMeasures().getIbu(), putProductCaptor.getValue().getTargetMeasures().getIbu());
       assertEquals(updateProductDto.getTargetMeasures().getPh(), putProductCaptor.getValue().getTargetMeasures().getPh());
       assertEquals(updateProductDto.getTargetMeasures().getMashTemperature(), putProductCaptor.getValue().getTargetMeasures().getMashTemperature());
       assertEquals(updateProductDto.getTargetMeasures().getGravity(), putProductCaptor.getValue().getTargetMeasures().getGravity());
       assertEquals(updateProductDto.getTargetMeasures().getYield(), putProductCaptor.getValue().getTargetMeasures().getYield());
       assertEquals(updateProductDto.getTargetMeasures().getBrewhouseDuration(), putProductCaptor.getValue().getTargetMeasures().getBrewhouseDuration());
       assertEquals(updateProductDto.getTargetMeasures().getFermentationDays(), putProductCaptor.getValue().getTargetMeasures().getFermentationDays());
       assertEquals(updateProductDto.getTargetMeasures().getConditioningDays(), putProductCaptor.getValue().getTargetMeasures().getConditioningDays());
       
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

       assertEquals(product.getTargetMeasures().getAbv(), productDto.getTargetMeasures().getAbv());
       assertEquals(product.getTargetMeasures().getIbu(), productDto.getTargetMeasures().getIbu());
       assertEquals(product.getTargetMeasures().getPh(), productDto.getTargetMeasures().getPh());
       assertEquals(product.getTargetMeasures().getMashTemperature(), productDto.getTargetMeasures().getMashTemperature());
       assertEquals(product.getTargetMeasures().getGravity(), productDto.getTargetMeasures().getGravity());
       assertEquals(product.getTargetMeasures().getYield(), productDto.getTargetMeasures().getYield());
       assertEquals(product.getTargetMeasures().getBrewhouseDuration(), productDto.getTargetMeasures().getBrewhouseDuration());
       assertEquals(product.getTargetMeasures().getFermentationDays(), productDto.getTargetMeasures().getFermentationDays());
       assertEquals(product.getTargetMeasures().getConditioningDays(), productDto.getTargetMeasures().getConditioningDays());
       
       assertEquals(product.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testPatchProduct() {
       ProductMeasuresDto targetMeasuresDto = new ProductMeasuresDto(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);  
       UpdateProductDto updateProductDto = new UpdateProductDto("testProduct", "testDescription", 2L, targetMeasuresDto, 1);
       
       Category productClass = new Category(1L, "testClass", null, null, null, null, null);
       Category type = new Category(2L, "testType", productClass, null, null, null, null);
       Category style = new Category(3L, "testStyle", type, null, null, null, null);
       ProductMeasures targetMeasures = new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
       
       Product product = new Product(1L, "testProduct", "testDescription", style, targetMeasures, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
   
       ArgumentCaptor<Product> patchProductCaptor = ArgumentCaptor.forClass(Product.class);
       
       doReturn(product).when(productService).patchProduct(eq(1L), patchProductCaptor.capture(), eq(updateProductDto.getCategoryId()));

       ProductDto productDto = productController.patchProduct(updateProductDto, 1L);
       
       //Assert patched product
       assertEquals(null, patchProductCaptor.getValue().getId());
       assertEquals(updateProductDto.getName(), patchProductCaptor.getValue().getName());
       assertEquals(updateProductDto.getDescription(), patchProductCaptor.getValue().getDescription());
       assertEquals(updateProductDto.getCategoryId(), patchProductCaptor.getValue().getCategory().getId());
       
       assertEquals(updateProductDto.getTargetMeasures().getAbv(), patchProductCaptor.getValue().getTargetMeasures().getAbv());
       assertEquals(updateProductDto.getTargetMeasures().getIbu(), patchProductCaptor.getValue().getTargetMeasures().getIbu());
       assertEquals(updateProductDto.getTargetMeasures().getPh(), patchProductCaptor.getValue().getTargetMeasures().getPh());
       assertEquals(updateProductDto.getTargetMeasures().getMashTemperature(), patchProductCaptor.getValue().getTargetMeasures().getMashTemperature());
       assertEquals(updateProductDto.getTargetMeasures().getGravity(), patchProductCaptor.getValue().getTargetMeasures().getGravity());
       assertEquals(updateProductDto.getTargetMeasures().getYield(), patchProductCaptor.getValue().getTargetMeasures().getYield());
       assertEquals(updateProductDto.getTargetMeasures().getBrewhouseDuration(), patchProductCaptor.getValue().getTargetMeasures().getBrewhouseDuration());
       assertEquals(updateProductDto.getTargetMeasures().getFermentationDays(), patchProductCaptor.getValue().getTargetMeasures().getFermentationDays());
       assertEquals(updateProductDto.getTargetMeasures().getConditioningDays(), patchProductCaptor.getValue().getTargetMeasures().getConditioningDays());
       
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

       assertEquals(product.getTargetMeasures().getAbv(), productDto.getTargetMeasures().getAbv());
       assertEquals(product.getTargetMeasures().getIbu(), productDto.getTargetMeasures().getIbu());
       assertEquals(product.getTargetMeasures().getPh(), productDto.getTargetMeasures().getPh());
       assertEquals(product.getTargetMeasures().getMashTemperature(), productDto.getTargetMeasures().getMashTemperature());
       assertEquals(product.getTargetMeasures().getGravity(), productDto.getTargetMeasures().getGravity());
       assertEquals(product.getTargetMeasures().getYield(), productDto.getTargetMeasures().getYield());
       assertEquals(product.getTargetMeasures().getBrewhouseDuration(), productDto.getTargetMeasures().getBrewhouseDuration());
       assertEquals(product.getTargetMeasures().getFermentationDays(), productDto.getTargetMeasures().getFermentationDays());
       assertEquals(product.getTargetMeasures().getConditioningDays(), productDto.getTargetMeasures().getConditioningDays());
       
       assertEquals(product.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testDeleteProduct() {
       productController.softDeleteProduct(1L);

       verify(productService, times(1)).softDeleteProduct(1L);
   }

}