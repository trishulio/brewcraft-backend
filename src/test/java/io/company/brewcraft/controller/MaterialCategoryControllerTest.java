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
import io.company.brewcraft.pojo.MaterialCategory;
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
       MaterialCategory category = new MaterialCategory(1L, "root", new MaterialCategory(2L, null, null, null, null, null, null), null, null, null, null);
   
       List<MaterialCategory> categoryList = List.of(category);
       Page<MaterialCategory> mPage = mock(Page.class);
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

       PageDto<MaterialCategoryDto> dto = materialCategoryController.getCategories(
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
       MaterialCategoryDto materialDto = dto.getContent().get(0);

       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testGetMaterialCategory() {
       MaterialCategory category = new MaterialCategory(1L, "root", new MaterialCategory(2L, null, null, null, null, null, null), null, null, null, null);

       doReturn(category).when(materialCategoryService).getCategory(1L);
       
       MaterialCategoryDto materialDto = materialCategoryController.getCategory(1L);
       
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
       AddMaterialCategoryDto addCategoryDto = new AddMaterialCategoryDto(2L, "categoryName");
              
       MaterialCategory category = new MaterialCategory(1L, "categoryName", new MaterialCategory(2L, null, null, null, null, null, null), null, null, null, 1);
       
       ArgumentCaptor<MaterialCategory> addedCategoryCaptor = ArgumentCaptor.forClass(MaterialCategory.class);
       
       doReturn(category).when(materialCategoryService).addCategory(eq(addCategoryDto.getParentCategoryId()), addedCategoryCaptor.capture());

       MaterialCategoryDto materialDto = materialCategoryController.addCategory(addCategoryDto);
       
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
       UpdateMaterialCategoryDto updateCategoryDto = new UpdateMaterialCategoryDto(2L, "categoryName", 1);
              
       MaterialCategory category = new MaterialCategory(1L, "categoryName", new MaterialCategory(2L, null, null, null, null, null, null), null, null, null, 1);

       ArgumentCaptor<MaterialCategory> putMaterialCaptor = ArgumentCaptor.forClass(MaterialCategory.class);
       
       doReturn(category).when(materialCategoryService).putCategory(eq(2L), eq(1L), putMaterialCaptor.capture());

       MaterialCategoryDto materialDto = materialCategoryController.putCategory(updateCategoryDto, 1L);
       
       //Assert put category
       assertEquals(null, putMaterialCaptor.getValue().getId());
       assertEquals(updateCategoryDto.getName(), putMaterialCaptor.getValue().getName());
       assertEquals(updateCategoryDto.getParentCategoryId(), 2L);
       assertEquals(updateCategoryDto.getVersion(), putMaterialCaptor.getValue().getVersion());

       //Assert returned category  
       assertEquals(category.getId(), materialDto.getId());
       assertEquals(category.getName(), materialDto.getName());
       assertEquals(category.getParentCategory().getId(), materialDto.getParentCategoryId());
       assertEquals(category.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testPatchMaterialCategory() {
       UpdateMaterialCategoryDto updateCategoryDto = new UpdateMaterialCategoryDto(2L, "categoryName", 1);
       
       MaterialCategory category = new MaterialCategory(1L, "categoryName", new MaterialCategory(2L, null, null, null, null, null, null), null, null, null, 1);

       ArgumentCaptor<MaterialCategory> patchMaterialCaptor = ArgumentCaptor.forClass(MaterialCategory.class);
       
       doReturn(category).when(materialCategoryService).patchCategory(eq(2L), eq(1L), patchMaterialCaptor.capture());

       MaterialCategoryDto materialDto = materialCategoryController.patchCategory(updateCategoryDto, 1L);
       
       //Assert patched category
       assertEquals(null, patchMaterialCaptor.getValue().getId());
       assertEquals(updateCategoryDto.getName(), patchMaterialCaptor.getValue().getName());
       assertEquals(updateCategoryDto.getParentCategoryId(), 2L);
       assertEquals(updateCategoryDto.getVersion(), patchMaterialCaptor.getValue().getVersion());

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