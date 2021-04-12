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

import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.repository.MaterialCategoryRepository;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class MaterialCategoryServiceImplTest {

    private MaterialCategoryService materialCategoryService;

    private MaterialCategoryRepository materialCategoryRepository;
    
    @BeforeEach
    public void init() {
        materialCategoryRepository = mock(MaterialCategoryRepository.class);
        
        materialCategoryService = new MaterialCategoryServiceImpl(materialCategoryRepository);
    }

    @Test
    public void testGetMaterialCategories_returnsMaterialCategories() throws Exception {
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(2L, null, null, null, null, null, null), Set.of(new MaterialCategory(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        List<MaterialCategory> materialCategoryEntities = Arrays.asList(materialCategoryEntity);
        
        Page<MaterialCategory> expectedMaterialCategoryEntities = new PageImpl<>(materialCategoryEntities);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(materialCategoryRepository.findAll(ArgumentMatchers.<Specification<MaterialCategory>>any(), pageableArgument.capture())).thenReturn(expectedMaterialCategoryEntities);

        Page<MaterialCategory> actualMaterialCategorys = materialCategoryService.getCategories(null, null, null, null, 0, 100, new HashSet<>(Arrays.asList("id")), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(1, actualMaterialCategorys.getNumberOfElements());
        
        MaterialCategory actualCategory = actualMaterialCategorys.get().findFirst().get();

        assertEquals(materialCategoryEntity.getId(), actualCategory.getId());
        assertEquals(materialCategoryEntity.getName(), actualCategory.getName());
        assertEquals(materialCategoryEntity.getParentCategory().getId(), actualCategory.getParentCategory().getId());
        assertEquals(materialCategoryEntity.getSubcategories().size(), actualCategory.getSubcategories().size());
        assertEquals(materialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), actualCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(materialCategoryEntity.getLastUpdated(), actualCategory.getLastUpdated());
        assertEquals(materialCategoryEntity.getCreatedAt(), actualCategory.getCreatedAt());
        assertEquals(materialCategoryEntity.getVersion(), actualCategory.getVersion());      
    }
    
    @Test
    public void testGetMaterialCategory_returnsMaterialCategory() throws Exception {
        Long id = 1L;
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(2L, null, null, null, null, null, null), Set.of(new MaterialCategory(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<MaterialCategory> expectedMaterialCategoryEntity = Optional.ofNullable(materialCategoryEntity);

        when(materialCategoryRepository.findById(id)).thenReturn(expectedMaterialCategoryEntity);

        MaterialCategory returnedCategory = materialCategoryService.getCategory(id);

        assertEquals(expectedMaterialCategoryEntity.get().getId(), returnedCategory.getId());
        assertEquals(expectedMaterialCategoryEntity.get().getName(), returnedCategory.getName());
        assertEquals(expectedMaterialCategoryEntity.get().getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(expectedMaterialCategoryEntity.get().getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(expectedMaterialCategoryEntity.get().getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(expectedMaterialCategoryEntity.get().getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(expectedMaterialCategoryEntity.get().getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(expectedMaterialCategoryEntity.get().getVersion(), returnedCategory.getVersion());
    }

    @Test
    public void testAddMaterialCategory_SavesMaterialCategoryWhenNoParentCategoryIsPassed() throws Exception {
        MaterialCategory newCategory = new MaterialCategory(null, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MaterialCategory> persistedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);
        
        when(materialCategoryRepository.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(materialCategoryEntity);

        MaterialCategory returnedCategory = materialCategoryService.addCategory(null, newCategory);
        
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
        assertEquals(materialCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(materialCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(materialCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(materialCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(materialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(materialCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(materialCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(materialCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testAddMaterialCategory_setsParentCategoryWhenParentCategoryIsPassed() throws Exception {
        Long parentCategoryId = 2L;

        MaterialCategory materialCategoryParent = new MaterialCategory();

        MaterialCategory newCategory = new MaterialCategory(null, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MaterialCategory> persistedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);

        when(materialCategoryRepository.findById(parentCategoryId)).thenReturn(Optional.of(materialCategoryParent));
        
        when(materialCategoryRepository.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(materialCategoryEntity);

        MaterialCategory actualMaterialCategory = materialCategoryService.addCategory(parentCategoryId, newCategory);
        
        //Assert persisted entity
        assertEquals(newCategory.getId(), persistedCategoryCaptor.getValue().getId());
        assertEquals(newCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(materialCategoryParent.getId(), persistedCategoryCaptor.getValue().getParentCategory().getId());
        assertEquals(newCategory.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(newCategory.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(newCategory.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(newCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(newCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(materialCategoryEntity.getId(), actualMaterialCategory.getId());
        assertEquals(materialCategoryEntity.getName(), actualMaterialCategory.getName());
        assertEquals(materialCategoryEntity.getParentCategory().getId(), actualMaterialCategory.getParentCategory().getId());
        assertEquals(materialCategoryEntity.getSubcategories().size(), actualMaterialCategory.getSubcategories().size());
        assertEquals(materialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), actualMaterialCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(materialCategoryEntity.getLastUpdated(), actualMaterialCategory.getLastUpdated());
        assertEquals(materialCategoryEntity.getCreatedAt(), actualMaterialCategory.getCreatedAt());
        assertEquals(materialCategoryEntity.getVersion(), actualMaterialCategory.getVersion()); 
    }
    
    @Test
    public void testAddMaterialCategory_throwsWhenParentCategoryDoesNotExist() throws Exception {
        Long parentCategoryId = 2L;
        
        MaterialCategory newCategory = new MaterialCategory(null, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(materialCategoryRepository.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            materialCategoryService.addCategory(parentCategoryId, newCategory);
            verify(materialCategoryRepository, times(0)).saveAndFlush(Mockito.any(MaterialCategory.class));
        });
    }
    
    @Test
    public void testPutMaterialCategory_successIfNoParentCategoryPassed() throws Exception {
        Long id = 1L;

        MaterialCategory putCategory = new MaterialCategory(null, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MaterialCategory> persistedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);

        when(materialCategoryRepository.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(materialCategoryEntity);

        MaterialCategory returnedCategory = materialCategoryService.putCategory(null, id, putCategory);
       
        //Assert persisted entity
        assertEquals(id, persistedCategoryCaptor.getValue().getId());
        assertEquals(putCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(putCategory.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(putCategory.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(putCategory.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        //assertEquals(null, persistedCategoryCaptor.getValue().getLastUpdated());
        //assertEquals(putCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(putCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(materialCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(materialCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(materialCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(materialCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(materialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(materialCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(materialCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(materialCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testPutMaterialCategory_setsParentCategoryIfExists() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;

        MaterialCategory putCategory = new MaterialCategory(null, "testName", new MaterialCategory(1L, null, null, null, null, null, null), Set.of(new MaterialCategory(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategory materialCategoryParent = new MaterialCategory();
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(1L, null, null, null, null, null, null), Set.of(new MaterialCategory(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MaterialCategory> persistedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);
        
        when(materialCategoryRepository.findById(id)).thenReturn(Optional.of(materialCategoryEntity));
        
        when(materialCategoryRepository.findById(parentCategoryId)).thenReturn(Optional.of(materialCategoryParent));

        when(materialCategoryRepository.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(materialCategoryEntity);

        MaterialCategory returnedCategory = materialCategoryService.putCategory(parentCategoryId, id, putCategory);
       
        //Assert persisted entity
        assertEquals(id, persistedCategoryCaptor.getValue().getId());
        assertEquals(putCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(materialCategoryParent.getId(), persistedCategoryCaptor.getValue().getParentCategory().getId());
        assertEquals(putCategory.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(putCategory.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(putCategory.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(putCategory.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(putCategory.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(materialCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(materialCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(materialCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(materialCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(materialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(materialCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(materialCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(materialCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
     
    @Test
    public void testPutMaterialCategory_throwsIfParentCategoryDoesNotExist() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;

        MaterialCategory putCategory = new MaterialCategory(null, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(materialCategoryRepository.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            materialCategoryService.putCategory(parentCategoryId, id, putCategory);
            verify(materialCategoryRepository, times(0)).saveAndFlush(Mockito.any(MaterialCategory.class));
        });
    }
    
    @Test
    public void testPatchMaterialCategory_successIfNoParentCategoryPassed() throws Exception {
        Long id = 1L;
        MaterialCategory patchedCategory = new MaterialCategory(1L, "updatedName", null, null, null, null, null);
        MaterialCategory existingMaterialCategoryEntity = new MaterialCategory(1L, "testName", null, Set.of(new MaterialCategory(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategory persistedMaterialCategoryEntity = new MaterialCategory(1L, "updatedName", null, Set.of(new MaterialCategory(2L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MaterialCategory> persistedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);

        when(materialCategoryRepository.findById(id)).thenReturn(Optional.of(existingMaterialCategoryEntity));
 
        when(materialCategoryRepository.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(persistedMaterialCategoryEntity);

        MaterialCategory returnedCategory = materialCategoryService.patchCategory(null, id, patchedCategory);
       
        //Assert persisted entity
        assertEquals(existingMaterialCategoryEntity.getId(), persistedCategoryCaptor.getValue().getId());
        assertEquals(patchedCategory.getName(), persistedCategoryCaptor.getValue().getName());
        assertEquals(existingMaterialCategoryEntity.getParentCategory(), persistedCategoryCaptor.getValue().getParentCategory());
        assertEquals(existingMaterialCategoryEntity.getSubcategories().size(), persistedCategoryCaptor.getValue().getSubcategories().size());
        assertEquals(existingMaterialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), persistedCategoryCaptor.getValue().getSubcategories().stream().findFirst().get().getId());
        assertEquals(existingMaterialCategoryEntity.getLastUpdated(), persistedCategoryCaptor.getValue().getLastUpdated());
        assertEquals(existingMaterialCategoryEntity.getCreatedAt(), persistedCategoryCaptor.getValue().getCreatedAt());
        assertEquals(existingMaterialCategoryEntity.getVersion(), persistedCategoryCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedMaterialCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(persistedMaterialCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(persistedMaterialCategoryEntity.getParentCategory(), returnedCategory.getParentCategory());
        assertEquals(persistedMaterialCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(persistedMaterialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(persistedMaterialCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(persistedMaterialCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(persistedMaterialCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testPatchMaterialCategory_setsParentCategoryIfExists() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;
        MaterialCategory patchedCategory = new MaterialCategory(1L, "updatedName", null, null, null, null, null);
        MaterialCategory parentCategoryEntity = new MaterialCategory();
        MaterialCategory existingCategoryEntity = new MaterialCategory(1L, "testName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        MaterialCategory materialCategoryEntity = new MaterialCategory(1L, "updatedName", new MaterialCategory(), Set.of(new MaterialCategory()), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MaterialCategory> persistedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);

        when(materialCategoryRepository.findById(parentCategoryId)).thenReturn(Optional.of(parentCategoryEntity));
        
        when(materialCategoryRepository.findById(id)).thenReturn(Optional.of(existingCategoryEntity));
 
        when(materialCategoryRepository.saveAndFlush(persistedCategoryCaptor.capture())).thenReturn(materialCategoryEntity);

        MaterialCategory returnedCategory = materialCategoryService.patchCategory(parentCategoryId, id, patchedCategory);
       
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
        assertEquals(materialCategoryEntity.getId(), returnedCategory.getId());
        assertEquals(materialCategoryEntity.getName(), returnedCategory.getName());
        assertEquals(materialCategoryEntity.getParentCategory().getId(), returnedCategory.getParentCategory().getId());
        assertEquals(materialCategoryEntity.getSubcategories().size(), returnedCategory.getSubcategories().size());
        assertEquals(materialCategoryEntity.getSubcategories().stream().findFirst().get().getId(), returnedCategory.getSubcategories().stream().findFirst().get().getId());
        assertEquals(materialCategoryEntity.getLastUpdated(), returnedCategory.getLastUpdated());
        assertEquals(materialCategoryEntity.getCreatedAt(), returnedCategory.getCreatedAt());
        assertEquals(materialCategoryEntity.getVersion(), returnedCategory.getVersion()); 
    }
    
    @Test
    public void testPatchMaterialCategory_throwsIfParentCategoryDoesNotExist() throws Exception {
        Long id = 1L;
        Long parentCategoryId = 2L;
        MaterialCategory material = new MaterialCategory();
        
        when(materialCategoryRepository.findById(parentCategoryId)).thenReturn(Optional.ofNullable(null));
      
        assertThrows(EntityNotFoundException.class, () -> {
            materialCategoryService.patchCategory(parentCategoryId, id, material);
            verify(materialCategoryRepository, times(0)).saveAndFlush(Mockito.any(MaterialCategory.class));
        });
    }
    
    @Test
    public void testPatchMaterialCategory_throwsIfCategoryDoesNotExist() throws Exception {
        Long id = 1L;
        MaterialCategory material = new MaterialCategory();
        
        when(materialCategoryRepository.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            materialCategoryService.patchCategory(null,id, material);
            verify(materialCategoryRepository, times(0)).saveAndFlush(Mockito.any(MaterialCategory.class));
        });
    }

    @Test
    public void testDeleteMaterialCategory_success() throws Exception {
        Long id = 1L;
        materialCategoryService.deleteCategory(id);
        
        verify(materialCategoryRepository, times(1)).deleteById(id);
    }
    
    @Test
    public void testMaterialCategoryExists_success() throws Exception {
        Long id = 1L;
        materialCategoryService.categoryExists(id);
        
        verify(materialCategoryRepository, times(1)).existsById(id);
    }
    
    @Test
    public void testMaterialCategoryService_classIsTransactional() throws Exception {
        Transactional transactional = materialCategoryService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testMaterialCategoryService_methodsAreNotTransactional() throws Exception {
        Method[] methods = materialCategoryService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
