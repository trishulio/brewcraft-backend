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

import io.company.brewcraft.dto.AddMaterialDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMaterialDto;
import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.SupportedUnits;

@SuppressWarnings("unchecked")
public class MaterialControllerTest {

   private MaterialController materialController;

   private MaterialService materialService;

   @BeforeEach
   public void init() {
       materialService = mock(MaterialService.class);

       materialController = new MaterialController(materialService);
   }

   @Test
   public void testGetMaterials() {
       MaterialCategory rootCategory = new MaterialCategory(1L, "root", null, null, null, null, null);
       MaterialCategory subcategory = new MaterialCategory(2L, "subcategory1", rootCategory, null, null, null, null);
       Material material = new Material(1L, "testMaterial", "testDescription", subcategory, "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
   
       List<Material> materialsList = List.of(material);
       Page<Material> mPage = mock(Page.class);
       doReturn(materialsList.stream()).when(mPage).stream();
       doReturn(100).when(mPage).getTotalPages();
       doReturn(1000L).when(mPage).getTotalElements();
       doReturn(mPage).when(materialService).getMaterials(
           Set.of(1L),
           Set.of(2L),
           Set.of("Ingredient"),
           1,
           10,
           Set.of("id"),
           true
       );

       PageDto<MaterialDto> dto = materialController.getMaterials(
               Set.of(1L),
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
       MaterialDto materialDto = dto.getContent().get(0);

       assertEquals(material.getId(), materialDto.getId());
       assertEquals(material.getName(), materialDto.getName());
       assertEquals(material.getDescription(), materialDto.getDescription());
       
       assertEquals(material.getCategory().getId(), materialDto.getCategory().getId());
       assertEquals(material.getCategory().getName(), materialDto.getCategory().getName());
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getCategory().getParentCategoryId());
       
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getMaterialClass().getId());
       assertEquals(material.getCategory().getParentCategory().getName(), materialDto.getMaterialClass().getName());
       assertEquals(null, materialDto.getMaterialClass().getParentCategoryId());

       assertEquals(material.getUPC(), materialDto.getUPC());
       assertEquals(material.getBaseQuantityUnit().getSymbol(), materialDto.getBaseQuantityUnit());
       assertEquals(material.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testGetMaterial() {
       MaterialCategory rootCategory = new MaterialCategory(1L, "root", null, null, null, null, null);
       MaterialCategory subcategory = new MaterialCategory(2L, "subcategory1", rootCategory, null, null, null, null);
       Material material = new Material(1L, "testMaterial", "testDescription", subcategory, "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

       doReturn(material).when(materialService).getMaterial(1L);
       
       MaterialDto materialDto = materialController.getMaterial(1L);
       
       assertEquals(material.getId(), materialDto.getId());
       assertEquals(material.getName(), materialDto.getName());
       assertEquals(material.getDescription(), materialDto.getDescription());
       
       assertEquals(material.getCategory().getId(), materialDto.getCategory().getId());
       assertEquals(material.getCategory().getName(), materialDto.getCategory().getName());
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getCategory().getParentCategoryId());
       
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getMaterialClass().getId());
       assertEquals(material.getCategory().getParentCategory().getName(), materialDto.getMaterialClass().getName());
       assertEquals(null, materialDto.getMaterialClass().getParentCategoryId());

       assertEquals(material.getUPC(), materialDto.getUPC());
       assertEquals(material.getBaseQuantityUnit().getSymbol(), materialDto.getBaseQuantityUnit());
       assertEquals(material.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testGetMaterial_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
       when(materialService.getMaterial(1L)).thenReturn(null);
       assertThrows(EntityNotFoundException.class, () -> materialController.getMaterial(1L), "Material not found with id: 1");
   }

   @Test
   public void testAddMaterial() {
       AddMaterialDto addMaterialDto = new AddMaterialDto("testMaterial", "testDescription", 2L, "testUPC", "kg");
              
       MaterialCategory rootCategory = new MaterialCategory(1L, "root", null, null, null, null, null);
       MaterialCategory subcategory = new MaterialCategory(2L, "subcategory1", rootCategory, null, null, null, null);
       Material material = new Material(1L, "testMaterial", "testDescription", subcategory, "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       
       ArgumentCaptor<Material> addedMaterialCaptor = ArgumentCaptor.forClass(Material.class);
       
       doReturn(material).when(materialService).addMaterial(addedMaterialCaptor.capture(), eq(addMaterialDto.getCategoryId()), eq(addMaterialDto.getBaseQuantityUnit()));

       MaterialDto materialDto = materialController.addMaterial(addMaterialDto);
       
       //Assert added material
       assertEquals(null, addedMaterialCaptor.getValue().getId());
       assertEquals(addMaterialDto.getName(), addedMaterialCaptor.getValue().getName());
       assertEquals(addMaterialDto.getDescription(), addedMaterialCaptor.getValue().getDescription());
       assertEquals(addMaterialDto.getCategoryId(), addedMaterialCaptor.getValue().getCategory().getId());
       assertEquals(addMaterialDto.getUPC(), addedMaterialCaptor.getValue().getUPC());
       assertEquals(addMaterialDto.getBaseQuantityUnit(), addedMaterialCaptor.getValue().getBaseQuantityUnit().getSymbol());        
       
       //Assert returned material  
       assertEquals(material.getId(), materialDto.getId());
       assertEquals(material.getName(), materialDto.getName());
       assertEquals(material.getDescription(), materialDto.getDescription());
       
       assertEquals(material.getCategory().getId(), materialDto.getCategory().getId());
       assertEquals(material.getCategory().getName(), materialDto.getCategory().getName());
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getCategory().getParentCategoryId());
       
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getMaterialClass().getId());
       assertEquals(material.getCategory().getParentCategory().getName(), materialDto.getMaterialClass().getName());
       assertEquals(null, materialDto.getMaterialClass().getParentCategoryId());

       assertEquals(material.getUPC(), materialDto.getUPC());
       assertEquals(material.getBaseQuantityUnit().getSymbol(), materialDto.getBaseQuantityUnit());
       assertEquals(material.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testPutMaterial() {
       UpdateMaterialDto updateMaterialDto = new UpdateMaterialDto("testMaterial", "testDescription", 2L, "testUPC", "kg", 1);
              
       MaterialCategory rootCategory = new MaterialCategory(1L, "root", null, null, null, null, null);
       MaterialCategory subcategory = new MaterialCategory(2L, "subcategory1", rootCategory, null, null, null, null);
       Material material = new Material(1L, "testMaterial", "testDescription", subcategory, "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       
       ArgumentCaptor<Material> putMaterialCaptor = ArgumentCaptor.forClass(Material.class);
       
       doReturn(material).when(materialService).putMaterial(eq(1L), putMaterialCaptor.capture(), eq(updateMaterialDto.getCategoryId()), eq(updateMaterialDto.getBaseQuantityUnit()));

       MaterialDto materialDto = materialController.putMaterial(updateMaterialDto, 1L);
       
       //Assert put material
       assertEquals(null, putMaterialCaptor.getValue().getId());
       assertEquals(updateMaterialDto.getName(), putMaterialCaptor.getValue().getName());
       assertEquals(updateMaterialDto.getDescription(), putMaterialCaptor.getValue().getDescription());
       assertEquals(updateMaterialDto.getCategoryId(), putMaterialCaptor.getValue().getCategory().getId());
       assertEquals(updateMaterialDto.getUPC(), putMaterialCaptor.getValue().getUPC());
       assertEquals(updateMaterialDto.getBaseQuantityUnit(), putMaterialCaptor.getValue().getBaseQuantityUnit().getSymbol());        
       assertEquals(updateMaterialDto.getVersion(), putMaterialCaptor.getValue().getVersion());        

       //Assert returned material  
       assertEquals(material.getId(), materialDto.getId());
       assertEquals(material.getName(), materialDto.getName());
       assertEquals(material.getDescription(), materialDto.getDescription());
       
       assertEquals(material.getCategory().getId(), materialDto.getCategory().getId());
       assertEquals(material.getCategory().getName(), materialDto.getCategory().getName());
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getCategory().getParentCategoryId());
       
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getMaterialClass().getId());
       assertEquals(material.getCategory().getParentCategory().getName(), materialDto.getMaterialClass().getName());
       assertEquals(null, materialDto.getMaterialClass().getParentCategoryId());

       assertEquals(material.getUPC(), materialDto.getUPC());
       assertEquals(material.getBaseQuantityUnit().getSymbol(), materialDto.getBaseQuantityUnit());
       assertEquals(material.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testPatchMaterial() {
       UpdateMaterialDto updateMaterialDto = new UpdateMaterialDto("testMaterial", "testDescription", 2L, "testUPC", "kg", 1);
       
       MaterialCategory rootCategory = new MaterialCategory(1L, "root", null, null, null, null, null);
       MaterialCategory subcategory = new MaterialCategory(2L, "subcategory1", rootCategory, null, null, null, null);
       Material material = new Material(1L, "testMaterial", "testDescription", subcategory, "testUPC", SupportedUnits.KILOGRAM, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
       
       ArgumentCaptor<Material> patchMaterialCaptor = ArgumentCaptor.forClass(Material.class);
       
       doReturn(material).when(materialService).patchMaterial(eq(1L), patchMaterialCaptor.capture(), eq(updateMaterialDto.getCategoryId()), eq(updateMaterialDto.getBaseQuantityUnit()));

       MaterialDto materialDto = materialController.patchMaterial(updateMaterialDto, 1L);
       
       //Assert patched material
       assertEquals(null, patchMaterialCaptor.getValue().getId());
       assertEquals(updateMaterialDto.getName(), patchMaterialCaptor.getValue().getName());
       assertEquals(updateMaterialDto.getDescription(), patchMaterialCaptor.getValue().getDescription());
       assertEquals(updateMaterialDto.getCategoryId(), patchMaterialCaptor.getValue().getCategory().getId());
       assertEquals(updateMaterialDto.getUPC(), patchMaterialCaptor.getValue().getUPC());
       assertEquals(updateMaterialDto.getBaseQuantityUnit(), patchMaterialCaptor.getValue().getBaseQuantityUnit().getSymbol());        
       assertEquals(updateMaterialDto.getVersion(), patchMaterialCaptor.getValue().getVersion());        

       //Assert returned material  
       assertEquals(material.getId(), materialDto.getId());
       assertEquals(material.getName(), materialDto.getName());
       assertEquals(material.getDescription(), materialDto.getDescription());
       
       assertEquals(material.getCategory().getId(), materialDto.getCategory().getId());
       assertEquals(material.getCategory().getName(), materialDto.getCategory().getName());
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getCategory().getParentCategoryId());
       
       assertEquals(material.getCategory().getParentCategory().getId(), materialDto.getMaterialClass().getId());
       assertEquals(material.getCategory().getParentCategory().getName(), materialDto.getMaterialClass().getName());
       assertEquals(null, materialDto.getMaterialClass().getParentCategoryId());

       assertEquals(material.getUPC(), materialDto.getUPC());
       assertEquals(material.getBaseQuantityUnit().getSymbol(), materialDto.getBaseQuantityUnit());
       assertEquals(material.getVersion(), materialDto.getVersion());
   }
   
   @Test
   public void testDeleteMaterial() {
       materialController.deleteMaterial(1L);

       verify(materialService, times(1)).deleteMaterial(1L);
   }

}