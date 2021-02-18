package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddMaterialCategoryDto;
import io.company.brewcraft.dto.MaterialCategoryDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMaterialCategoryDto;
import io.company.brewcraft.pojo.MaterialCategory;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.MaterialCategoryMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/materials/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialCategoryController {
    
    private MaterialCategoryService materialCategoryService;
    
    private MaterialCategoryMapper materialCategoryMapper = MaterialCategoryMapper.INSTANCE;
        
    public MaterialCategoryController(MaterialCategoryService materialCategoryService) {
        this.materialCategoryService = materialCategoryService;
    }
    
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<MaterialCategoryDto> getCategories(
            @RequestParam(required = false) Set<Long> ids,  @RequestParam(required = false) Set<String> names,  @RequestParam(required = false) Set<Long> parentCategoryIds,
            @RequestParam(required = false) Set<String> parentNames, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true") boolean orderAscending) {
            Page<MaterialCategory> materialCategoriesPage = materialCategoryService.getCategories(ids, names, parentCategoryIds, parentNames, page, size, sort, orderAscending);

            List<MaterialCategoryDto> materialCategoriesList = materialCategoriesPage.stream()
                    .map(materialCategory -> materialCategoryMapper.toDto(materialCategory)).collect(Collectors.toList());

            PageDto<MaterialCategoryDto> dto = new PageDto<MaterialCategoryDto>(materialCategoriesList, materialCategoriesPage.getTotalPages(), materialCategoriesPage.getTotalElements());
            
            return dto;
    }
    
    @GetMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE)
    public MaterialCategoryDto getCategory(@PathVariable Long categoryId) {
        Validator validator = new Validator();

        MaterialCategory materialCategory = materialCategoryService.getCategory(categoryId);
        
        validator.assertion(materialCategory != null, EntityNotFoundException.class, "MaterialCategory", categoryId.toString());

        return materialCategoryMapper.toDto(materialCategory);
    }
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialCategoryDto addCategory(@Valid @RequestBody AddMaterialCategoryDto addMaterialCategoryDto) {
        MaterialCategory materialCategory = materialCategoryMapper.fromDto(addMaterialCategoryDto);
        Long parentCategoryId = addMaterialCategoryDto.getParentCategoryId();
        
        MaterialCategory addedMaterialCategory = materialCategoryService.addCategory(parentCategoryId, materialCategory);
        
        return materialCategoryMapper.toDto(addedMaterialCategory);
    }
    
    @PutMapping("/{categoryId}")
    public MaterialCategoryDto putCategory(@Valid @RequestBody UpdateMaterialCategoryDto updateMaterialCategoryDto, @PathVariable Long categoryId) {
        MaterialCategory materialCategory = materialCategoryMapper.fromDto(updateMaterialCategoryDto);
        Long parentCategoryId = updateMaterialCategoryDto.getParentCategoryId();

        MaterialCategory putMaterialCategory = materialCategoryService.putCategory(parentCategoryId, categoryId, materialCategory);

        return materialCategoryMapper.toDto(putMaterialCategory);
    }
    
    @PatchMapping("/{categoryId}")
    public MaterialCategoryDto patchCategory(@Valid @RequestBody UpdateMaterialCategoryDto updateMaterialCategoryDto, @PathVariable Long categoryId) {
        MaterialCategory materialCategory = materialCategoryMapper.fromDto(updateMaterialCategoryDto);
        Long parentCategoryId = updateMaterialCategoryDto.getParentCategoryId();

        MaterialCategory patchedMaterialCategory = materialCategoryService.patchCategory(parentCategoryId, categoryId, materialCategory);
        
        return materialCategoryMapper.toDto(patchedMaterialCategory);
    }

    @DeleteMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE)
    public void deleteCategory(@PathVariable Long categoryId) {
        materialCategoryService.deleteCategory(categoryId);
    }    
    
}
