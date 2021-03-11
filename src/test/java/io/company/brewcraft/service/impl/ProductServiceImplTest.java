package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.ProductCategoryEntity;
import io.company.brewcraft.model.ProductEntity;
import io.company.brewcraft.model.ProductMeasuresEntity;
import io.company.brewcraft.pojo.Product;
import io.company.brewcraft.pojo.ProductMeasures;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.service.CategoryService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class ProductServiceImplTest {

    private ProductService productService;

    private ProductRepository productRepositoryMock;
    
    private CategoryService productCategoryServiceMock;
    
    @BeforeEach
    public void init() {
        productRepositoryMock = mock(ProductRepository.class);
        productCategoryServiceMock = mock(ProductCategoryServiceImpl.class);
        
        productService = new ProductServiceImpl(productRepositoryMock, productCategoryServiceMock);
    }

    @Test
    public void testGetProducts_returnsProducts() throws Exception {
        ProductEntity productEntity = new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        List<ProductEntity> productEntities = Arrays.asList(productEntity);
        
        Page<ProductEntity> expectedProductEntities = new PageImpl<>(productEntities);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(productRepositoryMock.findAll(ArgumentMatchers.<Specification<ProductEntity>>any(), pageableArgument.capture())).thenReturn(expectedProductEntities);

        Page<Product> actualProducts = productService.getProducts(null, null, null, 0, 100, new HashSet<>(Arrays.asList("id")), true);

        assertSame(0, pageableArgument.getValue().getPageNumber());
        assertSame(100, pageableArgument.getValue().getPageSize());
        assertSame(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertSame("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertSame(1, actualProducts.getNumberOfElements());
        
        Product actualProduct = actualProducts.get().findFirst().get();
        
        assertSame(productEntity.getId(), actualProduct.getId());
        assertSame(productEntity.getName(), actualProduct.getName());
        assertSame(productEntity.getDescription(), actualProduct.getDescription());
        assertSame(productEntity.getCategory().getId(), actualProduct.getCategory().getId());

        assertEquals(productEntity.getTargetMeasures().getAbv(), actualProduct.getTargetMeasures().getAbv());
        assertEquals(productEntity.getTargetMeasures().getIbu(), actualProduct.getTargetMeasures().getIbu());
        assertEquals(productEntity.getTargetMeasures().getPh(), actualProduct.getTargetMeasures().getPh());
        assertEquals(productEntity.getTargetMeasures().getMashTemperature(), actualProduct.getTargetMeasures().getMashTemperature());
        assertEquals(productEntity.getTargetMeasures().getGravity(), actualProduct.getTargetMeasures().getGravity());
        assertEquals(productEntity.getTargetMeasures().getYield(), actualProduct.getTargetMeasures().getYield());
        assertEquals(productEntity.getTargetMeasures().getBrewhouseDuration(), actualProduct.getTargetMeasures().getBrewhouseDuration());
        assertEquals(productEntity.getTargetMeasures().getFermentationDays(), actualProduct.getTargetMeasures().getFermentationDays());
        assertEquals(productEntity.getTargetMeasures().getConditioningDays(), actualProduct.getTargetMeasures().getConditioningDays());
             
        assertSame(productEntity.getLastUpdated(), actualProduct.getLastUpdated());
        assertSame(productEntity.getCreatedAt(), actualProduct.getCreatedAt());
        assertSame(productEntity.getVersion(), actualProduct.getVersion());
    }
    
    @Test
    public void testGetProduct_returnsProduct() throws Exception {
        Long id = 1L;
        ProductEntity productEntity = new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<ProductEntity> expectedProductEntity = Optional.ofNullable(productEntity);

        when(productRepositoryMock.findByIdsExcludeDeleted(Set.of(id))).thenReturn(expectedProductEntity);

        Product returnedProduct = productService.getProduct(id);

        assertEquals(expectedProductEntity.get().getId(), returnedProduct.getId());
        assertEquals(expectedProductEntity.get().getName(), returnedProduct.getName());
        assertEquals(expectedProductEntity.get().getDescription(), returnedProduct.getDescription());
        assertEquals(expectedProductEntity.get().getCategory().getId(), returnedProduct.getCategory().getId());

        assertEquals(expectedProductEntity.get().getTargetMeasures().getAbv(), returnedProduct.getTargetMeasures().getAbv());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getIbu(), returnedProduct.getTargetMeasures().getIbu());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getPh(), returnedProduct.getTargetMeasures().getPh());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getMashTemperature(), returnedProduct.getTargetMeasures().getMashTemperature());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getGravity(), returnedProduct.getTargetMeasures().getGravity());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getYield(), returnedProduct.getTargetMeasures().getYield());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getBrewhouseDuration(), returnedProduct.getTargetMeasures().getBrewhouseDuration());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getFermentationDays(), returnedProduct.getTargetMeasures().getFermentationDays());
        assertEquals(expectedProductEntity.get().getTargetMeasures().getConditioningDays(), returnedProduct.getTargetMeasures().getConditioningDays());
        
        assertEquals(expectedProductEntity.get().getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(expectedProductEntity.get().getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(expectedProductEntity.get().getVersion(), returnedProduct.getVersion());
    }

    @Test
    public void testAddProduct_SavesProduct() throws Exception {
        Product product = new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        ProductEntity newProduct = new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductEntity> persistedProductCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(newProduct);

        Product returnedProduct = productService.addProduct(product);
        
        //Assert persisted entity
        assertEquals(product.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(product.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(product.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(product.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());

        assertEquals(product.getTargetMeasures().getAbv(), persistedProductCaptor.getValue().getTargetMeasures().getAbv());
        assertEquals(product.getTargetMeasures().getIbu(), persistedProductCaptor.getValue().getTargetMeasures().getIbu());
        assertEquals(product.getTargetMeasures().getPh(), persistedProductCaptor.getValue().getTargetMeasures().getPh());
        assertEquals(product.getTargetMeasures().getMashTemperature(), persistedProductCaptor.getValue().getTargetMeasures().getMashTemperature());
        assertEquals(product.getTargetMeasures().getGravity(), persistedProductCaptor.getValue().getTargetMeasures().getGravity());
        assertEquals(product.getTargetMeasures().getYield(), persistedProductCaptor.getValue().getTargetMeasures().getYield());
        assertEquals(product.getTargetMeasures().getBrewhouseDuration(), persistedProductCaptor.getValue().getTargetMeasures().getBrewhouseDuration());
        assertEquals(product.getTargetMeasures().getFermentationDays(), persistedProductCaptor.getValue().getTargetMeasures().getFermentationDays());
        assertEquals(product.getTargetMeasures().getConditioningDays(), persistedProductCaptor.getValue().getTargetMeasures().getConditioningDays());
            
        assertEquals(product.getLastUpdated(), persistedProductCaptor.getValue().getLastUpdated());
        assertEquals(product.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(product.getVersion(), persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(newProduct.getId(), returnedProduct.getId());
        assertEquals(newProduct.getName(), returnedProduct.getName());
        assertEquals(newProduct.getDescription(), returnedProduct.getDescription());
        assertEquals(newProduct.getCategory().getId(), returnedProduct.getCategory().getId());
        
        assertEquals(newProduct.getTargetMeasures().getAbv(), returnedProduct.getTargetMeasures().getAbv());
        assertEquals(newProduct.getTargetMeasures().getIbu(), returnedProduct.getTargetMeasures().getIbu());
        assertEquals(newProduct.getTargetMeasures().getPh(), returnedProduct.getTargetMeasures().getPh());
        assertEquals(newProduct.getTargetMeasures().getMashTemperature(), returnedProduct.getTargetMeasures().getMashTemperature());
        assertEquals(newProduct.getTargetMeasures().getGravity(), returnedProduct.getTargetMeasures().getGravity());
        assertEquals(newProduct.getTargetMeasures().getYield(), returnedProduct.getTargetMeasures().getYield());
        assertEquals(newProduct.getTargetMeasures().getBrewhouseDuration(), returnedProduct.getTargetMeasures().getBrewhouseDuration());
        assertEquals(newProduct.getTargetMeasures().getFermentationDays(), returnedProduct.getTargetMeasures().getFermentationDays());
        assertEquals(newProduct.getTargetMeasures().getConditioningDays(), returnedProduct.getTargetMeasures().getConditioningDays());
        
        assertEquals(newProduct.getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(newProduct.getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(newProduct.getVersion(), returnedProduct.getVersion()); 
    }
    
    @Test
    public void testPutProduct_success() throws Exception {
        Long id = 1L;
        Product putProduct = new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductEntity productEntity = new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductEntity> persistedProductCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(productEntity);

        Product returnedProduct = productService.putProduct(id, putProduct);
       
        //Assert persisted entity
        assertEquals(putProduct.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(putProduct.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(putProduct.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(putProduct.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());
        
        assertEquals(putProduct.getTargetMeasures().getAbv(), persistedProductCaptor.getValue().getTargetMeasures().getAbv());
        assertEquals(putProduct.getTargetMeasures().getIbu(), persistedProductCaptor.getValue().getTargetMeasures().getIbu());
        assertEquals(putProduct.getTargetMeasures().getPh(), persistedProductCaptor.getValue().getTargetMeasures().getPh());
        assertEquals(putProduct.getTargetMeasures().getMashTemperature(), persistedProductCaptor.getValue().getTargetMeasures().getMashTemperature());
        assertEquals(putProduct.getTargetMeasures().getGravity(), persistedProductCaptor.getValue().getTargetMeasures().getGravity());
        assertEquals(putProduct.getTargetMeasures().getYield(), persistedProductCaptor.getValue().getTargetMeasures().getYield());
        assertEquals(putProduct.getTargetMeasures().getBrewhouseDuration(), persistedProductCaptor.getValue().getTargetMeasures().getBrewhouseDuration());
        assertEquals(putProduct.getTargetMeasures().getFermentationDays(), persistedProductCaptor.getValue().getTargetMeasures().getFermentationDays());
        assertEquals(putProduct.getTargetMeasures().getConditioningDays(), persistedProductCaptor.getValue().getTargetMeasures().getConditioningDays());
            
        assertEquals(null, persistedProductCaptor.getValue().getLastUpdated());
        //assertEquals(putProduct.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(putProduct.getVersion(), persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(productEntity.getId(), returnedProduct.getId());
        assertEquals(productEntity.getName(), returnedProduct.getName());
        assertEquals(productEntity.getDescription(), returnedProduct.getDescription());
        assertEquals(productEntity.getCategory().getId(), returnedProduct.getCategory().getId());

        assertEquals(productEntity.getTargetMeasures().getAbv(), returnedProduct.getTargetMeasures().getAbv());
        assertEquals(productEntity.getTargetMeasures().getIbu(), returnedProduct.getTargetMeasures().getIbu());
        assertEquals(productEntity.getTargetMeasures().getPh(), returnedProduct.getTargetMeasures().getPh());
        assertEquals(productEntity.getTargetMeasures().getMashTemperature(), returnedProduct.getTargetMeasures().getMashTemperature());
        assertEquals(productEntity.getTargetMeasures().getGravity(), returnedProduct.getTargetMeasures().getGravity());
        assertEquals(productEntity.getTargetMeasures().getYield(), returnedProduct.getTargetMeasures().getYield());
        assertEquals(productEntity.getTargetMeasures().getBrewhouseDuration(), returnedProduct.getTargetMeasures().getBrewhouseDuration());
        assertEquals(productEntity.getTargetMeasures().getFermentationDays(), returnedProduct.getTargetMeasures().getFermentationDays());
        assertEquals(productEntity.getTargetMeasures().getConditioningDays(), returnedProduct.getTargetMeasures().getConditioningDays());
        
        assertEquals(productEntity.getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(productEntity.getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(productEntity.getVersion(), returnedProduct.getVersion()); 
    }
    
    @Test
    public void testPatchProduct_success() throws Exception {
        Long id = 1L;
        Product patchedProduct = new Product(1L, "testProduct", "testDescription", new Category(1L, null, null, null, null, null, null), new ProductMeasures(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductEntity existingProductEntity = new ProductEntity(1L, "testProduct", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductEntity persistedProductEntity = new ProductEntity(1L, "updatedName", "testDescription", new ProductCategoryEntity(1L, null, null, null, null, null, null), new ProductMeasuresEntity(1L, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductEntity> persistedProductCaptor = ArgumentCaptor.forClass(ProductEntity.class);

        when(productRepositoryMock.findByIdsExcludeDeleted(Set.of(id))).thenReturn(Optional.of(existingProductEntity));
 
        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(persistedProductEntity);

        Product returnedProduct = productService.patchProduct(id, patchedProduct);
       
        //Assert persisted entity
        assertEquals(existingProductEntity.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(patchedProduct.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(existingProductEntity.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(existingProductEntity.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());
       
        assertEquals(existingProductEntity.getTargetMeasures().getAbv(), persistedProductCaptor.getValue().getTargetMeasures().getAbv());
        assertEquals(existingProductEntity.getTargetMeasures().getIbu(), persistedProductCaptor.getValue().getTargetMeasures().getIbu());
        assertEquals(existingProductEntity.getTargetMeasures().getPh(), persistedProductCaptor.getValue().getTargetMeasures().getPh());
        assertEquals(existingProductEntity.getTargetMeasures().getMashTemperature(), persistedProductCaptor.getValue().getTargetMeasures().getMashTemperature());
        assertEquals(existingProductEntity.getTargetMeasures().getGravity(), persistedProductCaptor.getValue().getTargetMeasures().getGravity());
        assertEquals(existingProductEntity.getTargetMeasures().getYield(), persistedProductCaptor.getValue().getTargetMeasures().getYield());
        assertEquals(existingProductEntity.getTargetMeasures().getBrewhouseDuration(), persistedProductCaptor.getValue().getTargetMeasures().getBrewhouseDuration());
        assertEquals(existingProductEntity.getTargetMeasures().getFermentationDays(), persistedProductCaptor.getValue().getTargetMeasures().getFermentationDays());
        assertEquals(existingProductEntity.getTargetMeasures().getConditioningDays(), persistedProductCaptor.getValue().getTargetMeasures().getConditioningDays());
            
        assertEquals(existingProductEntity.getLastUpdated(), persistedProductCaptor.getValue().getLastUpdated());
        assertEquals(existingProductEntity.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(existingProductEntity.getVersion(), persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedProductEntity.getId(), returnedProduct.getId());
        assertEquals(persistedProductEntity.getName(), returnedProduct.getName());
        assertEquals(persistedProductEntity.getDescription(), returnedProduct.getDescription());
        assertEquals(persistedProductEntity.getCategory().getId(), returnedProduct.getCategory().getId());
        
        assertEquals(persistedProductEntity.getTargetMeasures().getAbv(), returnedProduct.getTargetMeasures().getAbv());
        assertEquals(persistedProductEntity.getTargetMeasures().getIbu(), returnedProduct.getTargetMeasures().getIbu());
        assertEquals(persistedProductEntity.getTargetMeasures().getPh(), returnedProduct.getTargetMeasures().getPh());
        assertEquals(persistedProductEntity.getTargetMeasures().getMashTemperature(), returnedProduct.getTargetMeasures().getMashTemperature());
        assertEquals(persistedProductEntity.getTargetMeasures().getGravity(), returnedProduct.getTargetMeasures().getGravity());
        assertEquals(persistedProductEntity.getTargetMeasures().getYield(), returnedProduct.getTargetMeasures().getYield());
        assertEquals(persistedProductEntity.getTargetMeasures().getBrewhouseDuration(), returnedProduct.getTargetMeasures().getBrewhouseDuration());
        assertEquals(persistedProductEntity.getTargetMeasures().getFermentationDays(), returnedProduct.getTargetMeasures().getFermentationDays());
        assertEquals(persistedProductEntity.getTargetMeasures().getConditioningDays(), returnedProduct.getTargetMeasures().getConditioningDays());
       
        assertEquals(persistedProductEntity.getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(persistedProductEntity.getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(persistedProductEntity.getVersion(), returnedProduct.getVersion()); 
    }
    
    @Test
    public void testPatchProduct_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Product product = new Product();
        
        when(productRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            productService.patchProduct(id, product);
            verify(productRepositoryMock, times(0)).saveAndFlush(Mockito.any(ProductEntity.class));
        });
    }
    
    @Test
    public void testSoftDeleteProduct_success() throws Exception {
        Long id = 1L;
        productService.softDeleteProduct(id);
        
        verify(productRepositoryMock, times(1)).softDeleteByIds(Set.of(1L));
    }

    @Test
    public void testDeleteProduct_success() throws Exception {
        Long id = 1L;
        productService.deleteProduct(id);
        
        verify(productRepositoryMock, times(1)).deleteById(id);
    }
    
    @Test
    public void testProductExists_success() throws Exception {
        Long id = 1L;
        productService.productExists(id);
        
        verify(productRepositoryMock, times(1)).existsById(id);
    }
    
    @Test
    public void testProductService_classIsTransactional() throws Exception {
        Transactional transactional = productService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testProductService_methodsAreNotTransactional() throws Exception {
        Method[] methods = productService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
