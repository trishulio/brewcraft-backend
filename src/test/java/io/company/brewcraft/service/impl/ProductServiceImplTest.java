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
import java.util.ArrayList;
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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasure;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.repository.ProductRepository;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.ProductMeasureService;
import io.company.brewcraft.service.ProductMeasureValueService;
import io.company.brewcraft.service.ProductService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class ProductServiceImplTest {

    private ProductService productService;

    private ProductRepository productRepositoryMock;
        
    private ProductCategoryService productCategoryServiceMock;
    
    private ProductMeasureValueService productMeasureValueServiceMock;
    
    private ProductMeasureService productMeasureServiceMock;
    
    @BeforeEach
    public void init() {
        productRepositoryMock = mock(ProductRepository.class);
        productCategoryServiceMock = mock(ProductCategoryServiceImpl.class);
        productMeasureValueServiceMock = mock(ProductMeasureValueServiceImpl.class);
        productMeasureServiceMock = mock(ProductMeasureServiceImpl.class);
        
        productService = new ProductServiceImpl(productRepositoryMock, productCategoryServiceMock, productMeasureValueServiceMock, productMeasureServiceMock);
    }

    @Test
    public void testGetProducts_returnsProducts() throws Exception {
        Product productEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        List<Product> productEntities = Arrays.asList(productEntity);
        
        Page<Product> expectedProductEntities = new PageImpl<>(productEntities);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(productRepositoryMock.findAll(ArgumentMatchers.<Specification<Product>>any(), pageableArgument.capture())).thenReturn(expectedProductEntities);

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
        assertSame(productEntity.getTargetMeasures(), actualProduct.getTargetMeasures());
        assertSame(productEntity.getLastUpdated(), actualProduct.getLastUpdated());
        assertSame(productEntity.getCreatedAt(), actualProduct.getCreatedAt());
        assertSame(productEntity.getVersion(), actualProduct.getVersion());
    }
    
    @Test
    public void testGetProduct_returnsProduct() throws Exception {
        Long id = 1L;
        Product productEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<Product> expectedProductEntity = Optional.ofNullable(productEntity);

        when(productRepositoryMock.findById(id)).thenReturn(expectedProductEntity);

        Product returnedProduct = productService.getProduct(id);

        assertEquals(expectedProductEntity.get().getId(), returnedProduct.getId());
        assertEquals(expectedProductEntity.get().getName(), returnedProduct.getName());
        assertEquals(expectedProductEntity.get().getDescription(), returnedProduct.getDescription());
        assertEquals(expectedProductEntity.get().getCategory().getId(), returnedProduct.getCategory().getId());
        assertEquals(expectedProductEntity.get().getTargetMeasures(), returnedProduct.getTargetMeasures());
        assertEquals(expectedProductEntity.get().getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(expectedProductEntity.get().getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(expectedProductEntity.get().getVersion(), returnedProduct.getVersion());
    }

    @Test
    public void testAddProduct_SavesProduct() throws Exception {
        Product product = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        Product newProduct = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Product> persistedProductCaptor = ArgumentCaptor.forClass(Product.class);

        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv")));
        
        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(newProduct);

        Product returnedProduct = productService.addProduct(product);
        
        //Assert persisted entity
        assertEquals(product.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(product.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(product.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(product.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());
        assertEquals(product.getTargetMeasures(), persistedProductCaptor.getValue().getTargetMeasures());
        assertEquals(product.getLastUpdated(), persistedProductCaptor.getValue().getLastUpdated());
        assertEquals(product.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(product.getVersion(), persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(newProduct.getId(), returnedProduct.getId());
        assertEquals(newProduct.getName(), returnedProduct.getName());
        assertEquals(newProduct.getDescription(), returnedProduct.getDescription());
        assertEquals(newProduct.getCategory().getId(), returnedProduct.getCategory().getId());
        assertEquals(newProduct.getTargetMeasures(), returnedProduct.getTargetMeasures());
        assertEquals(newProduct.getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(newProduct.getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(newProduct.getVersion(), returnedProduct.getVersion()); 
    }
    
    @Test
    public void testAddProduct_throwsIllegalArgumentException() throws Exception {
        Product product = new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("unknown"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv")));
              
        assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(product);
            verify(productRepositoryMock, times(0)).saveAndFlush(Mockito.any(Product.class));
        });
    }
    
    @Test
    public void testPutProduct_successWhenNoExistingEntity() throws Exception {
        Long id = 1L;        
        Product putProduct = new Product(1L, "product1", "new desc", new ProductCategory(2L, null, null, null, null, null, null), new ArrayList<>(List.of(new ProductMeasureValue(1L,new ProductMeasure("ibu"), "200", new Product()))), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Product productEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ArrayList<>(List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product()))), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Product> persistedProductCaptor = ArgumentCaptor.forClass(Product.class);

        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv"), new ProductMeasure("ibu")));
        
        when(productMeasureValueServiceMock.merge(productEntity.getTargetMeasures(), putProduct.getTargetMeasures())).thenReturn(List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())));
        
        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(putProduct);

        Product returnedProduct = productService.putProduct(id, putProduct);
       
        //Assert persisted entity
        assertEquals(putProduct.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(putProduct.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(putProduct.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(putProduct.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());
        assertEquals(putProduct.getTargetMeasures(), persistedProductCaptor.getValue().getTargetMeasures());
        assertEquals(putProduct.getLastUpdated(), persistedProductCaptor.getValue().getLastUpdated());
        assertEquals(putProduct.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(putProduct.getVersion(), persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(putProduct.getId(), returnedProduct.getId());
        assertEquals(putProduct.getName(), returnedProduct.getName());
        assertEquals(putProduct.getDescription(), returnedProduct.getDescription());
        assertEquals(putProduct.getCategory().getId(), returnedProduct.getCategory().getId());
        assertEquals(putProduct.getTargetMeasures(), returnedProduct.getTargetMeasures());
        assertEquals(putProduct.getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(putProduct.getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(putProduct.getVersion(), returnedProduct.getVersion()); 
    }
    
    @Test
    public void testPutProduct_successWhenExistingEntity() throws Exception {
        Long id = 1L;
        
        List<ProductMeasureValue> list = new ArrayList<>();
        list.add(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product()));
        
        Product putProduct = new Product(1L, "testProductupdated", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), list, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Product existingProductEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), list, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Product productEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), list, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Product> persistedProductCaptor = ArgumentCaptor.forClass(Product.class);

        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv")));
        
        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(existingProductEntity));
        
        when(productMeasureValueServiceMock.merge(productEntity.getTargetMeasures(), putProduct.getTargetMeasures())).thenReturn(List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product())));
        
        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(putProduct);

        Product returnedProduct = productService.putProduct(id, putProduct);
       
        //Assert persisted entity
        assertEquals(putProduct.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(putProduct.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(putProduct.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(putProduct.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());
        assertEquals(putProduct.getTargetMeasures(), persistedProductCaptor.getValue().getTargetMeasures());
        assertEquals(putProduct.getLastUpdated(), persistedProductCaptor.getValue().getLastUpdated());
        assertEquals(putProduct.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(1, persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(putProduct.getId(), returnedProduct.getId());
        assertEquals(putProduct.getName(), returnedProduct.getName());
        assertEquals(putProduct.getDescription(), returnedProduct.getDescription());
        assertEquals(putProduct.getCategory().getId(), returnedProduct.getCategory().getId());
        assertEquals(putProduct.getTargetMeasures(), returnedProduct.getTargetMeasures());
        assertEquals(putProduct.getLastUpdated(), returnedProduct.getLastUpdated());
        assertEquals(putProduct.getCreatedAt(), returnedProduct.getCreatedAt());
        assertEquals(putProduct.getVersion(), returnedProduct.getVersion()); 
    }
    
    @Test
    public void testPutProduct_throwsIllegalArgumentException() throws Exception {
        Product product = new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("unknown"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv")));
              
        assertThrows(IllegalArgumentException.class, () -> {
            productService.putProduct(1L, product);
            verify(productRepositoryMock, times(0)).saveAndFlush(Mockito.any(Product.class));
        });
    }
    
    @Test
    public void testPatchProduct_success() throws Exception {
        Long id = 1L;
        Product patchedProduct = new Product(null, "updatedName", null, null, new ArrayList<>(List.of(new ProductMeasureValue(1L,new ProductMeasure("ibu"), "200", new Product()))), null, null, null, 1);
        Product existingProductEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ArrayList<>(List.of(new ProductMeasureValue(1L,new ProductMeasure("abv"), "100", new Product()))), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Product persistedProductEntity = new Product(1L, "updatedName", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), new ArrayList<>(List.of(new ProductMeasureValue(1L,new ProductMeasure("ibu"), "200", new Product()))), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Product> persistedProductCaptor = ArgumentCaptor.forClass(Product.class);

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(existingProductEntity));
 
        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv"), new ProductMeasure("ibu")));

        when(productMeasureValueServiceMock.merge(existingProductEntity.getTargetMeasures(), patchedProduct.getTargetMeasures())).thenReturn(List.of(new ProductMeasureValue(1L,new ProductMeasure("ibu"), "200", new Product())));
        
        when(productRepositoryMock.saveAndFlush(persistedProductCaptor.capture())).thenReturn(persistedProductEntity);

        Product returnedProduct = productService.patchProduct(id, patchedProduct);
       
        //Assert persisted entity
        assertEquals(existingProductEntity.getId(), persistedProductCaptor.getValue().getId());
        assertEquals(patchedProduct.getName(), persistedProductCaptor.getValue().getName());
        assertEquals(existingProductEntity.getDescription(), persistedProductCaptor.getValue().getDescription());
        assertEquals(existingProductEntity.getCategory().getId(), persistedProductCaptor.getValue().getCategory().getId());
        assertEquals(patchedProduct.getTargetMeasures(), persistedProductCaptor.getValue().getTargetMeasures());   
        assertEquals(existingProductEntity.getLastUpdated(), persistedProductCaptor.getValue().getLastUpdated());
        assertEquals(existingProductEntity.getCreatedAt(), persistedProductCaptor.getValue().getCreatedAt());
        assertEquals(existingProductEntity.getVersion(), persistedProductCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedProductEntity.getId(), returnedProduct.getId());
        assertEquals(persistedProductEntity.getName(), returnedProduct.getName());
        assertEquals(persistedProductEntity.getDescription(), returnedProduct.getDescription());
        assertEquals(persistedProductEntity.getCategory().getId(), returnedProduct.getCategory().getId());
        assertEquals(persistedProductEntity.getTargetMeasures(), returnedProduct.getTargetMeasures());
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
            verify(productRepositoryMock, times(0)).saveAndFlush(Mockito.any(Product.class));
        });
    }
    
    @Test
    public void testPatchProduct_throwsIllegalArgumentException() throws Exception {
        Product product = new Product(null, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), List.of(new ProductMeasureValue(1L,new ProductMeasure("unknown"), "100", new Product())), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv")));
              
        assertThrows(IllegalArgumentException.class, () -> {
            productService.patchProduct(1L, product);
            verify(productRepositoryMock, times(0)).saveAndFlush(Mockito.any(Product.class));
        });
    }
    
    @Test
    public void testPatchProduct_throwsOptimisticLockingException() throws Exception {
        Long id = 1L;        
        Product patchedProduct = new Product(null, "updatedName", null, null, null, null, null, null, 1);
        Product existingProductEntity = new Product(1L, "testProduct", "testDescription", new ProductCategory(1L, null, null, null, null, null, null), null, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2);

        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(existingProductEntity));
 
        when(productMeasureServiceMock.getAllProductMeasures()).thenReturn(List.of(new ProductMeasure("abv")));
       
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            productService.patchProduct(1L, patchedProduct);
            verify(productRepositoryMock, times(0)).saveAndFlush(Mockito.any(Product.class));
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
