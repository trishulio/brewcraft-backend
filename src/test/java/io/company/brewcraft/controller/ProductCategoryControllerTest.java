package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddCategoryDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.CategoryWithParentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class ProductCategoryControllerTest {
   private ProductCategoryController productCategoryController;

   private ProductCategoryService productCategoryService;

   @BeforeEach
   public void init() {
       productCategoryService = mock(ProductCategoryService.class);

       productCategoryController = new ProductCategoryController(productCategoryService, new AttributeFilter());
   }

   @Test
   public void testGetProductCategories() {
       ProductCategory category = new ProductCategory(1L, "root", new ProductCategory(2L), null, null, null, null);

       List<ProductCategory> categoryList = List.of(category);
       Page<ProductCategory> mPage = mock(Page.class);
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
           new TreeSet<>(List.of("id")),
           true
       );

       PageDto<CategoryDto> dto = productCategoryController.getCategories(
           Set.of(1L),
           Set.of("Lager"),
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
       CategoryDto productDto = dto.getContent().get(0);

       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategoryId());
       assertEquals(category.getVersion(), productDto.getVersion());
   }

   @Test
   public void testGetProductCategory() {
       ProductCategory category = new ProductCategory(1L, "root", new ProductCategory(2L, "parent", null, null, null, null, null), null, null, null, null);

       doReturn(category).when(productCategoryService).getCategory(1L);

       CategoryWithParentDto productDto = productCategoryController.getCategory(1L);

       assertEquals(category.getId(), productDto.getId());
       assertEquals(category.getName(), productDto.getName());
       assertEquals(category.getParentCategory().getId(), productDto.getParentCategory().getId());
       assertEquals(category.getParentCategory().getName(), productDto.getParentCategory().getName());
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

       ProductCategory category = new ProductCategory(1L, "categoryName", new ProductCategory(2L), null, null, null, 1);

       ArgumentCaptor<ProductCategory> addedCategoryCaptor = ArgumentCaptor.forClass(ProductCategory.class);

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

       ProductCategory category = new ProductCategory(1L, "categoryName", new ProductCategory(2L), null, null, null, 1);

       ArgumentCaptor<ProductCategory> putCategoryCaptor = ArgumentCaptor.forClass(ProductCategory.class);

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

       ProductCategory category = new ProductCategory(1L, "categoryName", new ProductCategory(2L), null, null, null, 1);

       ArgumentCaptor<ProductCategory> patchCategoryCaptor = ArgumentCaptor.forClass(ProductCategory.class);

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