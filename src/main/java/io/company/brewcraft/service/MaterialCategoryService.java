package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.MaterialCategory;

public interface MaterialCategoryService {
    public Page<MaterialCategory> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentIds, Set<String> parentNames,
            int page, int size, SortedSet<String> sort, boolean orderAscending);

    public MaterialCategory getCategory(Long categoryId);

    public MaterialCategory addCategory(Long parentCategoryId, MaterialCategory materialCategory);

    public MaterialCategory putCategory(Long parentCategoryId, Long categoryId, MaterialCategory material);

    public MaterialCategory patchCategory(Long parentCategoryId, Long categoryId, MaterialCategory material);

    public void deleteCategory(Long categoryId);

    public boolean categoryExists(Long categoryId);

 }
