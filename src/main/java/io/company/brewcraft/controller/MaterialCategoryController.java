package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
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

import io.company.brewcraft.dto.AddCategoryDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.CategoryWithParentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.MaterialCategoryMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/materials/categories")
public class MaterialCategoryController extends BaseController {
    private MaterialCategoryService materialCategoryService;

    private MaterialCategoryMapper materialCategoryMapper = MaterialCategoryMapper.INSTANCE;

    public MaterialCategoryController(MaterialCategoryService materialCategoryService, AttributeFilter filter) {
        super(filter);
        this.materialCategoryService = materialCategoryService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<CategoryDto> getCategories(
        @RequestParam(required = false) Set<Long> ids,
        @RequestParam(required = false) Set<String> names,
        @RequestParam(required = false) Set<Long> parentCategoryIds,
        @RequestParam(required = false) Set<String> parentNames,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {
        Page<MaterialCategory> materialCategoriesPage = materialCategoryService.getCategories(ids, names, parentCategoryIds, parentNames, page, size, sort, orderAscending);

        List<CategoryDto> materialCategoriesList = materialCategoriesPage.stream()
                                                                         .map(materialCategory -> materialCategoryMapper.toDto(materialCategory))
                                                                         .toList();

        PageDto<CategoryDto> dto = new PageDto<CategoryDto>(materialCategoriesList, materialCategoriesPage.getTotalPages(), materialCategoriesPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryWithParentDto getCategory(@PathVariable Long categoryId) {
        MaterialCategory materialCategory = materialCategoryService.getCategory(categoryId);

        Validator.assertion(materialCategory != null, EntityNotFoundException.class, "MaterialCategory", categoryId.toString());

        return materialCategoryMapper.toCategoryWithParentDto(materialCategory);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody AddCategoryDto addMaterialCategoryDto) {
        MaterialCategory materialCategory = materialCategoryMapper.fromDto(addMaterialCategoryDto);
        Long parentCategoryId = addMaterialCategoryDto.getParentCategoryId();

        MaterialCategory addedMaterialCategory = materialCategoryService.addCategory(parentCategoryId, materialCategory);

        return materialCategoryMapper.toDto(addedMaterialCategory);
    }

    @PutMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto putCategory(@Valid @RequestBody UpdateCategoryDto updateMaterialCategoryDto, @PathVariable Long categoryId) {
        MaterialCategory materialCategory = materialCategoryMapper.fromDto(updateMaterialCategoryDto);
        Long parentCategoryId = updateMaterialCategoryDto.getParentCategoryId();

        MaterialCategory putMaterialCategory = materialCategoryService.putCategory(parentCategoryId, categoryId, materialCategory);

        return materialCategoryMapper.toDto(putMaterialCategory);
    }

    @PatchMapping(value = "/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto patchCategory(@Valid @RequestBody UpdateCategoryDto updateMaterialCategoryDto, @PathVariable Long categoryId) {
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
