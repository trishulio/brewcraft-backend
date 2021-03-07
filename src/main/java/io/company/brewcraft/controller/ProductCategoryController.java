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

import io.company.brewcraft.dto.AddCategoryDto;
import io.company.brewcraft.dto.CategoryDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateCategoryDto;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.service.CategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.ProductCategoryMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/products/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryController {
    
    private CategoryService productCategoryService;
    
    private ProductCategoryMapper productCategoryMapper = ProductCategoryMapper.INSTANCE;
        
    public ProductCategoryController(CategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
    
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<CategoryDto> getCategories(
            @RequestParam(required = false) Set<Long> ids,  @RequestParam(required = false) Set<String> names,  @RequestParam(required = false) Set<Long> parentCategoryIds,
            @RequestParam(required = false) Set<String> parentNames, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true") boolean orderAscending) {
            Page<Category> categoriesPage = productCategoryService.getCategories(ids, names, parentCategoryIds, parentNames, page, size, sort, orderAscending);

            List<CategoryDto> productCategoriesList = categoriesPage.stream()
                    .map(productCategory -> productCategoryMapper.toDto(productCategory)).collect(Collectors.toList());

            PageDto<CategoryDto> dto = new PageDto<CategoryDto>(productCategoriesList, categoriesPage.getTotalPages(), categoriesPage.getTotalElements());
            
            return dto;
    }
    
    @GetMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE)
    public CategoryDto getCategory(@PathVariable Long categoryId) {
        Validator validator = new Validator();

        Category productCategory = productCategoryService.getCategory(categoryId);
        
        validator.assertion(productCategory != null, EntityNotFoundException.class, "ProductCategory", categoryId.toString());

        return productCategoryMapper.toDto(productCategory);
    }
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody AddCategoryDto addProductCategoryDto) {
        Category productCategory = productCategoryMapper.fromDto(addProductCategoryDto);
        Long parentCategoryId = addProductCategoryDto.getParentCategoryId();
        
        Category addedProductCategory = productCategoryService.addCategory(parentCategoryId, productCategory);
        
        return productCategoryMapper.toDto(addedProductCategory);
    }
    
    @PutMapping("/{categoryId}")
    public CategoryDto putCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto, @PathVariable Long categoryId) {
        Category productCategory = productCategoryMapper.fromDto(updateCategoryDto);
        Long parentCategoryId = updateCategoryDto.getParentCategoryId();

        Category putProductCategory = productCategoryService.putCategory(parentCategoryId, categoryId, productCategory);

        return productCategoryMapper.toDto(putProductCategory);
    }
    
    @PatchMapping("/{categoryId}")
    public CategoryDto patchCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto, @PathVariable Long categoryId) {
        Category productCategory = productCategoryMapper.fromDto(updateCategoryDto);
        Long parentCategoryId = updateCategoryDto.getParentCategoryId();

        Category patchedProductCategory = productCategoryService.patchCategory(parentCategoryId, categoryId, productCategory);
        
        return productCategoryMapper.toDto(patchedProductCategory);
    }

    @DeleteMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE)
    public void deleteCategory(@PathVariable Long categoryId) {
        productCategoryService.deleteCategory(categoryId);
    }    
    
}
