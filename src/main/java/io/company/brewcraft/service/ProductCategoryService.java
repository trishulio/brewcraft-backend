package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.ProductCategory;

public interface ProductCategoryService {

    public Page<ProductCategory> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentIds, Set<String> parentNames, 
            int page, int size, Set<String> sort, boolean orderAscending);
    
    public ProductCategory getCategory(Long categoryId);

    public ProductCategory addCategory(Long parentCategoryId, ProductCategory productCategory);
    
    public ProductCategory putCategory(Long parentCategoryId, Long categoryId, ProductCategory productCategory);
    
    public ProductCategory patchCategory(Long parentCategoryId, Long categoryId, ProductCategory productCategory);

    public void deleteCategory(Long categoryId);
    
    public boolean categoryExists(Long categoryId);
    
 }
