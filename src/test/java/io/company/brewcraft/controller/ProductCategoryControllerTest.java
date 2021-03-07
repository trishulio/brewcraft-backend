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
import io.company.brewcraft.service.CategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@SuppressWarnings("unchecked")
public class ProductCategoryControllerTest {

   private ProductCategoryController productCategoryController;

   private CategoryService productCategoryService;

   @BeforeEach
   public void init() {
       productCategoryService = mock(CategoryService.class);

       productCategoryController = new ProductCategoryController(productCategoryService);
   }

   @Test
   public void testGetProductCategories() {
       Category category = new Category(1L, "root", new Category(2L, null, null, null, null, null, null), null, null, null, null);
   
       List<Category> categoryList = List.of(category);
       Page<Category> mPage = mock(Page.class);
       doReturn(categoryList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(productCategoryService).getCategories(
           Set.of(1L),
           Set.of("Lager"),
           Set.of(2L),
           Set.of("Beer"),
           1,
           10,
           Set.of("id"),
           true
       );

       PageDto<CategoryDto> dto = productCategoryController.getCategories(
               Set.of(1L),
               Set.of("Lager"),
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
       CategoryDto productDto = dto.getContent().get(0);

       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategoryId());
       assertEquals(category.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testGetProductCategory() {
       Category category = new Category(1L, "root", new Category(2L, null, null, null, null, null, null), null, null, null, null);

       doReturn(category).when(productCategoryService).getCategory(1L);
       
       CategoryDto productDto = productCategoryController.getCategory(1L);
       
       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategoryId());
       assertEquals(category.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testGetProductCategory_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(productCategoryService.getCategory(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> productCategoryController.getCategory(1L), "Product category not found with id: 1");
   }

   @Test
   public void testAddProductCategory() {
       AddCategoryDto addCategoryDto = new AddCategoryDto(2L, "categoryName");
              
       Category category = new Category(1L, "categoryName", new Category(2L, null, null, null, null, null, null), null, null, null, 1);
       
       ArgumentCaptor<Category> addedCategoryCaptor = ArgumentCaptor.forClass(Category.class);
       
       doReturn(category).when(productCategoryService).addCategory(eq(addCategoryDto.getParentCategoryId()), addedCategoryCaptor.capture());

       CategoryDto productDto = productCategoryController.addCategory(addCategoryDto);
       
       //Assert added category
       assertEquals(null, addedCategoryCaptor.getValue().getId());
       assertEquals(addCategoryDto.getName(), addedCategoryCaptor.getValue().getName());
       assertEquals(addCategoryDto.getParentCategoryId(), 2L);
       
       //Assert returned category  
       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategoryId());
       assertEquals(category.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testPutProductCategory() {
       UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(2L, "categoryName", 1);
              
       Category category = new Category(1L, "categoryName", new Category(2L, null, null, null, null, null, null), null, null, null, 1);

       ArgumentCaptor<Category> putCategoryCaptor = ArgumentCaptor.forClass(Category.class);
       
       doReturn(category).when(productCategoryService).putCategory(eq(2L), eq(1L), putCategoryCaptor.capture());

       CategoryDto productDto = productCategoryController.putCategory(updateCategoryDto, 1L);
       
       //Assert put category
       assertEquals(null, putCategoryCaptor.getValue().getId());
       assertEquals(updateCategoryDto.getName(), putCategoryCaptor.getValue().getName());
       assertEquals(updateCategoryDto.getParentCategoryId(), 2L);
       assertEquals(updateCategoryDto.getVersion(), putCategoryCaptor.getValue().getVersion());

       //Assert returned category  
       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategoryId());
       assertEquals(category.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testPatchProductCategory() {
       UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(2L, "categoryName", 1);
       
       Category category = new Category(1L, "categoryName", new Category(2L, null, null, null, null, null, null), null, null, null, 1);

       ArgumentCaptor<Category> patchCategoryCaptor = ArgumentCaptor.forClass(Category.class);
       
       doReturn(category).when(productCategoryService).patchCategory(eq(2L), eq(1L), patchCategoryCaptor.capture());

       CategoryDto productDto = productCategoryController.patchCategory(updateCategoryDto, 1L);
       
       //Assert patched category
       assertEquals(null, patchCategoryCaptor.getValue().getId());
       assertEquals(updateCategoryDto.getName(), patchCategoryCaptor.getValue().getName());
       assertEquals(updateCategoryDto.getParentCategoryId(), 2L);
       assertEquals(updateCategoryDto.getVersion(), patchCategoryCaptor.getValue().getVersion());

       //Assert returned category  
       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategoryId());
       assertEquals(category.getVersion(), productDto.getVersion());
   }
   
   @Test
   public void testDeleteProductCategory() {
       productCategoryController.deleteCategory(1L);

       verify(productCategoryService, times(1)).deleteCategory(1L);
   }

}