package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateCategory;
import io.company.brewcraft.pojo.Category;

public interface MaterialCategoryService {

    public Page<Category> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentIds, Set<String> parentNames, 
            int page, int size, Set<String> sort, boolean orderAscending);
    
    public Category getCategory(Long categoryId);

    public Category addCategory(Long parentCategoryId, Category materialCategory);
    
    public Category putCategory(Long parentCategoryId, Long categoryId, UpdateCategory material);
    
    public Category patchCategory(Long parentCategoryId, Long categoryId, UpdateCategory material);

    public void deleteCategory(Long categoryId);
    
    public boolean categoryExists(Long categoryId);
    
 }
