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
import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.service.ProductCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.ProductCategoryMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/products/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryController extends BaseController {

    private ProductCategoryService productCategoryService;

    private ProductCategoryMapper productCategoryMapper = ProductCategoryMapper.INSTANCE;

    public ProductCategoryController(ProductCategoryService productCategoryService, AttributeFilter filter) {
        super(filter);
        this.productCategoryService = productCategoryService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<CategoryDto> getCategories(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false) Set<String> names,
            @RequestParam(required = false, name = "parent_category_ids") Set<Long> parentCategoryIds,
            @RequestParam(required = false, name = "parent_names") Set<String> parentNames,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {
        Page<ProductCategory> categoriesPage = productCategoryService.getCategories(ids, names, parentCategoryIds, parentNames, page, size, sort, orderAscending);

        List<CategoryDto> productCategoriesList = categoriesPage.stream()
                                                                .map(productCategory -> productCategoryMapper.toDto(productCategory))
                                                                .collect(Collectors.toList());

        PageDto<CategoryDto> dto = new PageDto<CategoryDto>(productCategoriesList, categoriesPage.getTotalPages(), categoriesPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE)
    public CategoryWithParentDto getCategory(@PathVariable Long categoryId) {
        ProductCategory productCategory = productCategoryService.getCategory(categoryId);

        Validator.assertion(productCategory != null, EntityNotFoundException.class, "ProductCategory", categoryId.toString());

        return productCategoryMapper.toCategoryWithParentDto(productCategory);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody AddCategoryDto addProductCategoryDto) {
        ProductCategory productCategory = productCategoryMapper.fromDto(addProductCategoryDto);
        Long parentCategoryId = addProductCategoryDto.getParentCategoryId();

        ProductCategory addedProductCategory = productCategoryService.addCategory(parentCategoryId, productCategory);

        return productCategoryMapper.toDto(addedProductCategory);
    }

    @PutMapping("/{categoryId}")
    public CategoryDto putCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto, @PathVariable Long categoryId) {
        ProductCategory productCategory = productCategoryMapper.fromDto(updateCategoryDto);
        Long parentCategoryId = updateCategoryDto.getParentCategoryId();

        ProductCategory putProductCategory = productCategoryService.putCategory(parentCategoryId, categoryId, productCategory);

        return productCategoryMapper.toDto(putProductCategory);
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto patchCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto, @PathVariable Long categoryId) {
        ProductCategory productCategory = productCategoryMapper.fromDto(updateCategoryDto);
        Long parentCategoryId = updateCategoryDto.getParentCategoryId();

        ProductCategory patchedProductCategory = productCategoryService.patchCategory(parentCategoryId, categoryId, productCategory);

        return productCategoryMapper.toDto(patchedProductCategory);
    }

    @DeleteMapping(value = "/{categoryId}", consumes = MediaType.ALL_VALUE)
    public void deleteCategory(@PathVariable Long categoryId) {
        productCategoryService.deleteCategory(categoryId);
    }
}
