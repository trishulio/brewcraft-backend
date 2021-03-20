package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.*;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@SuppressWarnings("unchecked")
public class MaterialCategoryControllerTest {

   private MaterialCategoryController materialCategoryController;

   private MaterialCategoryService materialCategoryService;

   @BeforeEach
   public void init() {
       materialCategoryService = mock(MaterialCategoryService.class);

       materialCategoryController = new MaterialCategoryController(materialCategoryService);
   }

   @Test
   public void testGetMaterialCategories() {
       Category category = new Category(1L, "root", new Category(2L, null, null, null, null, null, null), null, null, null, null);
   
       List<Category> categoryList = List.of(category);
       Page<Category> mPage = mock(Page.class);
       doReturn(categoryList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(materialCategoryService).getCategories(
           Set.of(1L),
           Set.of("Hop"),
           Set.of(2L),
           Set.of("Ingredient"),
           1,
           10,
           Set.of("id"),
           true
       );

       PageDto<CategoryDto> dto = materialCategoryController.getCategories(
               Set.of(1L),
               Set.of("Hop"),
               Set.of(2L),
               Set.of("Ingredient"),
               1,
               10,
               Set.of("id"),
               true
       );

       assertEquals(100, dto.getTotalPages());
       assertEquals(1000L, dto.getTotalElements());
       assertEquals(1, dto.getContent().size());
       CategoryDto materialDto = dto.getContent().get(0);

       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testGetMaterialCategory() {
       Category category = new Category(1L, "root", new Category(2L, null, null, null, null, null, null), null, null, null, null);

       doReturn(category).when(materialCategoryService).getCategory(1L);
       
       CategoryDto materialDto = materialCategoryController.getCategory(1L);
       
       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testGetMaterialCategory_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(materialCategoryService.getCategory(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> materialCategoryController.getCategory(1L), "Material category not found with id: 1");
   }

   @Test
   public void testAddMaterialCategory() {
       AddCategoryDto addCategoryDto = new AddCategoryDto(2L, "categoryName");
              
       Category category = new Category(1L, "categoryName", new Category(2L, null, null, null, null, null, null), null, null, null, 1);
       
       ArgumentCaptor<Category> addedCategoryCaptor = ArgumentCaptor.forClass(Category.class);
       
       doReturn(category).when(materialCategoryService).addCategory(eq(addCategoryDto.getParentCategoryId()), addedCategoryCaptor.capture());

       CategoryDto materialDto = materialCategoryController.addCategory(addCategoryDto);
       
       //Assert added category
       assertEquals(null, addedCategoryCaptor.getValue().getId());
       assertEquals(addCategoryDto.getName(), addedCategoryCaptor.getValue().getName());
       assertEquals(addCategoryDto.getParentCategoryId(), 2L);
       
       //Assert returned category  
       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testPutMaterialCategory() {
       UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(2L, "categoryName", 1);
              
       Category category = new Category(1L, "categoryName", new Category(2L, null, null, null, null, null, null), null, null, null, 1);

       ArgumentCaptor<Category> putCategoryCaptor = ArgumentCaptor.forClass(Category.class);
       
       doReturn(category).when(materialCategoryService).putCategory(eq(2L), eq(1L), putCategoryCaptor.capture());

       CategoryDto materialDto = materialCategoryController.putCategory(updateCategoryDto, 1L);
       
       //Assert put category
       assertEquals(null, putCategoryCaptor.getValue().getId());
       assertEquals(updateCategoryDto.getName(), putCategoryCaptor.getValue().getName());
       assertEquals(updateCategoryDto.getParentCategoryId(), 2L);
       assertEquals(updateCategoryDto.getVersion(), putCategoryCaptor.getValue().getVersion());

       //Assert returned category  
       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testPatchMaterialCategory() {
       UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(2L, "categoryName", 1);
       
       Category category = new Category(1L, "categoryName", new Category(2L, null, null, null, null, null, null), null, null, null, 1);

       ArgumentCaptor<Category> patchCategoryCaptor = ArgumentCaptor.forClass(Category.class);
       
       doReturn(category).when(materialCategoryService).patchCategory(eq(2L), eq(1L), patchCategoryCaptor.capture());

       CategoryDto materialDto = materialCategoryController.patchCategory(updateCategoryDto, 1L);
       
       //Assert patched category
       assertEquals(null, patchCategoryCaptor.getValue().getId());
       assertEquals(updateCategoryDto.getName(), patchCategoryCaptor.getValue().getName());
       assertEquals(updateCategoryDto.getParentCategoryId(), 2L);
       assertEquals(updateCategoryDto.getVersion(), patchCategoryCaptor.getValue().getVersion());

       //Assert returned category  
       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testDeleteMaterialCategory() {
       materialCategoryController.deleteCategory(1L);

       verify(materialCategoryService, times(1)).deleteCategory(1L);
   }

}