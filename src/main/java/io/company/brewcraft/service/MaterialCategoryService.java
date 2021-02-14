package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateMaterialCategory;
import io.company.brewcraft.pojo.MaterialCategory;

public interface MaterialCategoryService {

    public Page<MaterialCategory> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentIds, Set<String> parentNames, 
            int page, int size, Set<String> sort, boolean orderAscending);
    
    public MaterialCategory getCategory(Long categoryId);

    public MaterialCategory addCategory(Long parentCategoryId, MaterialCategory materialCategory);
    
    public MaterialCategory putCategory(Long parentCategoryId, Long categoryId, UpdateMaterialCategory material);
    
    public MaterialCategory patchCategory(Long parentCategoryId, Long categoryId, UpdateMaterialCategory material);

    public void deleteCategory(Long categoryId);
    
    public boolean categoryExists(Long categoryId);
    
 }
