package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.repository.ProductCategoryRepository;
import io.company.brewcraft.service.CategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class ProductCategoryServiceImplTest {

    private CategoryService productCategoryService;

    private ProductCategoryRepository productCategoryRepositoryMock;
    
    @BeforeEach
    public void init() {
        productCategoryRepositoryMock = mock(ProductCategoryRepository.class);
        
        productCategoryService = new ProductCategoryServiceImpl(productCategoryRepositoryMock);
    }

    @Test
    public void testGetProductCategories_returnsProductCategories() throws Exception {
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(2L, null, null, null, null, null, null), Set.of(new ProductCategoryEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        List<ProductCategoryEntity> productCategoryEntities = Arrays.asList(productCategoryEntity);
        
        Page<ProductCategoryEntity> expectedProductCategoryEntities = new PageImpl<>(productCategoryEntities);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(productCategoryRepositoryMock.findAll(ArgumentMatchers.<Specification<ProductCategoryEntity>>any(), pageableArgument.capture())).thenReturn(expectedProductCategoryEntities);

        Page<Category> actualProductCategorys = productCategoryService.getCategories(null, null, null, null, 0, 100, new HashSet<>(Arrays.asList("id")), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(1, actualProductCategorys.getNumberOfElements());
        
        Category actualCategory = actualProductCategorys.get().findFirst().get();

        assertEquals(productCategoryEntity.getId(), actualCategory.getId());
        assertEquals(productCategoryEntity.getName(), actualCategory.getName());
        assertEquals(productCategoryEntity.getParentCategory().getId(), actualCategory.getParentCategory().getId());
        assertEquals(productCategoryEntity.getSubcategories().size(), actualCategory.getSubcategories().size());
        assertEquals(productCategoryEntity.getSubcategories().stream().findFirst().get().getId(), actualCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(productCategoryEntity.getLastUpdated(), actualCategory.getLastUpdated());
        assertEquals(productCategoryEntity.getCreatedAt(), actualCategory.getCreatedAt());
        assertEquals(productCategoryEntity.getVersion(), actualCategory.getVersion());      
    }
    
    @Test
    public void testGetProductCategory_returnsProductCategory() throws Exception {
        Long id = 1L;
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(2L, null, null, null, null, null, null), Set.of(new ProductCategoryEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<ProductCategoryEntity> expectedProductCategoryEntity = Optional.ofNullable(productCategoryEntity);

        when(productCategoryRepositoryMock.findById(id)).thenReturn(expectedProductCategoryEntity);

        Category returnedCategory = productCategoryService.getCategory(id);

        assertEquals(expectedProductCategoryEntity.get().getId(), returnedCategory.getId());
        assertEquals(expectedProductCategoryEntity.get().getName(), returnedCategory.getName());
        assertEquals(expectedProductCategoryEntity.get().getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(expectedProductCategoryEntity.get().getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(expectedProductCategoryEntity.get().getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(expectedProductCategoryEntity.get().getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(expectedProductCategoryEntity.get().getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(expectedProductCategoryEntity.get().getVersion(), returnedCategory.getVersion());
    }

    @Test
    public void testAddProductCategory_SavesProductCategoryWhenNoParentCategoryIsPassed() throws Exception {
        Category newCategory = new Category(null, "testName", new Category(), Set.of(new Category()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(), Set.of(new ProductCategoryEntity()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductCategoryEntity> persistedCategoryCaptor = ArgumentCaptor.forClass(ProductCategoryEntity.class);
        
        when(productCategoryRepositoryMock.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(productCategoryEntity);

        Category returnedCategory = productCategoryService.addCategory(null, newCategory);
        
        //Assert persisted entity
        assertEquals(newCategory.getId(), persistedCategoryCaptor.getValue().getId());
        assertEquals(newCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(newCategory.getParentCategory().getId(), persistedCategoryCaptor.getValue().getParentCategory().getId());
        assertEquals(newCategory.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(newCategory.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(newCategory.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(newCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(newCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(productCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(productCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(productCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(productCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(productCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(productCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(productCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(productCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testAddProductCategory_setsParentCategoryWhenParentCategoryIsPassed() throws Exception {
        Long parentCategoryId = 2L;

        ProductCategoryEntity productCategoryParent = new ProductCategoryEntity();

        Category newCategory = new Category(null, "testName", new Category(), Set.of(new Category()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(), Set.of(new ProductCategoryEntity()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductCategoryEntity> persistedCategoryCaptor = ArgumentCaptor.forClass(ProductCategoryEntity.class);

        when(productCategoryRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.of(productCategoryParent));
        
        when(productCategoryRepositoryMock.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(productCategoryEntity);

        Category actualProductCategory = productCategoryService.addCategory(parentCategoryId, newCategory);
        
        //Assert persisted entity
        assertEquals(newCategory.getId(), persistedCategoryCaptor.getValue().getId());
        assertEquals(newCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(productCategoryParent.getId(), persistedCategoryCaptor.getValue().getParentCategory().getId());
        assertEquals(newCategory.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(newCategory.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(newCategory.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(newCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(newCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(productCategoryEntity.getId(), actualProductCategory.getId());
        assertEquals(productCategoryEntity.getName(), actualProductCategory.getName());
        assertEquals(productCategoryEntity.getParentCategory().getId(), actualProductCategory.getParentCategory().getId());
        assertEquals(productCategoryEntity.getSubcategories().size(), actualProductCategory.getSubcategories().size());
        assertEquals(productCategoryEntity.getSubcategories().stream().findFirst().get().getId(), actualProductCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(productCategoryEntity.getLastUpdated(), actualProductCategory.getLastUpdated());
        assertEquals(productCategoryEntity.getCreatedAt(), actualProductCategory.getCreatedAt());
        assertEquals(productCategoryEntity.getVersion(), actualProductCategory.getVersion()); 
    }
    
    @Test
    public void testAddProductCategory_throwsWhenParentCategoryDoesNotExist() throws Exception {
        Long parentCategoryId = 2L;
        
        Category newCategory = new Category(null, "testName", new Category(), Set.of(new Category()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(productCategoryRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            productCategoryService.addCategory(parentCategoryId, newCategory);
            verify(productCategoryRepositoryMock, times(0)).saveAndFlush(Mockito.any(ProductCategoryEntity.class));
        });
    }
    
    @Test
    public void testPutProductCategory_successIfNoParentCategoryPassed() throws Exception {
        Long id = 1L;

        Category putCategory = new Category(null, "testName", new Category(), Set.of(new Category()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(), Set.of(new ProductCategoryEntity()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductCategoryEntity> persistedCategoryCaptor = ArgumentCaptor.forClass(ProductCategoryEntity.class);

        when(productCategoryRepositoryMock.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(productCategoryEntity);

        Category returnedCategory = productCategoryService.putCategory(null, id, putCategory);
       
        //Assert persisted entity
        assertEquals(id, persistedCategoryCaptor.getValue().getId());
        assertEquals(putCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(putCategory.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(putCategory.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(putCategory.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(null, persistedCategoryCaptor.getValue().getLastUpdated());
        //assertEquals(putCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(putCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(productCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(productCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(productCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(productCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(productCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(productCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(productCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(productCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testPutProductCategory_setsParentCategoryIfExists() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;

        Category putCategory = new Category(null, "testName", new Category(1L, null, null, null, null, null, null), Set.of(new Category(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductCategoryEntity productCategoryParent = new ProductCategoryEntity();
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(1L, null, null, null, null, null, null), Set.of(new ProductCategoryEntity(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductCategoryEntity> persistedCategoryCaptor = ArgumentCaptor.forClass(ProductCategoryEntity.class);
        
        when(productCategoryRepositoryMock.findById(id)).thenReturn(Optional.of(productCategoryEntity));
        
        when(productCategoryRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.of(productCategoryParent));

        when(productCategoryRepositoryMock.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(productCategoryEntity);

        Category returnedCategory = productCategoryService.putCategory(parentCategoryId, id, putCategory);
       
        //Assert persisted entity
        assertEquals(id, persistedCategoryCaptor.getValue().getId());
        assertEquals(putCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(productCategoryParent.getId(), persistedCategoryCaptor.getValue().getParentCategory().getId());
        assertEquals(putCategory.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(putCategory.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(putCategory.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(putCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(putCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(productCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(productCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(productCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(productCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(productCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(productCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(productCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(productCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
     
    @Test
    public void testPutProductCategory_throwsIfParentCategoryDoesNotExist() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;

        Category putCategory = new Category(null, "testName", new Category(), Set.of(new Category()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(productCategoryRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            productCategoryService.putCategory(parentCategoryId, id, putCategory);
            verify(productCategoryRepositoryMock, times(0)).saveAndFlush(Mockito.any(ProductCategoryEntity.class));
        });
    }
    
    @Test
    public void testPatchProductCategory_successIfNoParentCategoryPassed() throws Exception {
        Long id = 1L;
        Category patchedCategory = new Category(1L, "updatedName", null, null, null, null, null);
        ProductCategoryEntity existingProductCategoryEntity = new ProductCategoryEntity(1L, "testName", null, Set.of(new ProductCategoryEntity(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductCategoryEntity persistedProductCategoryEntity = new ProductCategoryEntity(1L, "updatedName", null, Set.of(new ProductCategoryEntity(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductCategoryEntity> persistedCategoryCaptor = ArgumentCaptor.forClass(ProductCategoryEntity.class);

        when(productCategoryRepositoryMock.findById(id)).thenReturn(Optional.of(existingProductCategoryEntity));
 
        when(productCategoryRepositoryMock.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(persistedProductCategoryEntity);

        Category returnedCategory = productCategoryService.patchCategory(null, id, patchedCategory);
       
        //Assert persisted entity
        assertEquals(existingProductCategoryEntity.getId(), persistedCategoryCaptor.getValue().getId());
        assertEquals(patchedCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(existingProductCategoryEntity.getParentCategory(), persistedCategoryCaptor.getValue().getParentCategory());
        assertEquals(existingProductCategoryEntity.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(existingProductCategoryEntity.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(existingProductCategoryEntity.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(existingProductCategoryEntity.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(existingProductCategoryEntity.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedProductCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(persistedProductCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(persistedProductCategoryEntity.getParentCategory(), returnedCategory.getParentCategory());
        assertEquals(persistedProductCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(persistedProductCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(persistedProductCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(persistedProductCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(persistedProductCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testPatchProductCategory_setsParentCategoryIfExists() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;
        Category patchedCategory = new Category(1L, "updatedName", null, null, null, null, null);
        ProductCategoryEntity parentCategoryEntity = new ProductCategoryEntity();
        ProductCategoryEntity existingCategoryEntity = new ProductCategoryEntity(1L, "testName", new ProductCategoryEntity(), Set.of(new ProductCategoryEntity()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(1L, "updatedName", new ProductCategoryEntity(), Set.of(new ProductCategoryEntity()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<ProductCategoryEntity> persistedCategoryCaptor = ArgumentCaptor.forClass(ProductCategoryEntity.class);

        when(productCategoryRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.of(parentCategoryEntity));
        
        when(productCategoryRepositoryMock.findById(id)).thenReturn(Optional.of(existingCategoryEntity));
 
        when(productCategoryRepositoryMock.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(productCategoryEntity);

        Category returnedCategory = productCategoryService.patchCategory(parentCategoryId, id, patchedCategory);
       
        //Assert persisted entity
        assertEquals(existingCategoryEntity.getId(), persistedCategoryCaptor.getValue().getId());
        assertEquals(patchedCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(parentCategoryEntity.getId(), persistedCategoryCaptor.getValue().getParentCategory().getId());
        assertEquals(existingCategoryEntity.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(existingCategoryEntity.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(existingCategoryEntity.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(existingCategoryEntity.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(existingCategoryEntity.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(productCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(productCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(productCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(productCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(productCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(productCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(productCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(productCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testPatchProductCategory_throwsIfParentCategoryDoesNotExist() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;
        Category category = new Category();
        
        when(productCategoryRepositoryMock.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));
      
        assertThrows(EntityNotFoundException.class, () -> {
            productCategoryService.patchCategory(parentCategoryId, id, category);
            verify(productCategoryRepositoryMock, times(0)).saveAndFlush(Mockito.any(ProductCategoryEntity.class));
        });
    }
    
    @Test
    public void testPatchProductCategory_throwsIfCategoryDoesNotExist() throws Exception {
        Long id = 1L;
        Category category = new Category();
        
        when(productCategoryRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            productCategoryService.patchCategory(null,id, category);
            verify(productCategoryRepositoryMock, times(0)).saveAndFlush(Mockito.any(ProductCategoryEntity.class));
        });
    }

    @Test
    public void testDeleteProductCategory_success() throws Exception {
        Long id = 1L;
        productCategoryService.deleteCategory(id);
        
        verify(productCategoryRepositoryMock, times(1)).deleteById(id);
    }
    
    @Test
    public void testMProductCategoryExists_success() throws Exception {
        Long id = 1L;
        productCategoryService.categoryExists(id);
        
        verify(productCategoryRepositoryMock, times(1)).existsById(id);
    }
    
    @Test
    public void testProductCategoryService_classIsTransactional() throws Exception {
        Transactional transactional = productCategoryService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testProductCategoryService_methodsAreNotTransactional() throws Exception {
        Method[] methods = productCategoryService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
