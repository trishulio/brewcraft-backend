package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.repository.MaterialCategoryRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class MaterialCategoryServiceImpl extends BaseService implements MaterialCategoryService {
    private MaterialCategoryRepository materialCategoryRepository;

    public MaterialCategoryServiceImpl(MaterialCategoryRepository materialCategoryRepository) {
        this.materialCategoryRepository = materialCategoryRepository;
    }

    @Override
    public Page<MaterialCategory> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentCategoryIds, Set<String> parentNames,
            int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<MaterialCategory> spec = WhereClauseBuilder.builder()
                .in(MaterialCategory.FIELD_ID, ids)
                .in(MaterialCategory.FIELD_NAME, names)
                .in(new String[] { MaterialCategory.FIELD_PARENT_CATEGORY, MaterialCategory.FIELD_ID }, parentCategoryIds)
                .in(new String[] { MaterialCategory.FIELD_PARENT_CATEGORY, MaterialCategory.FIELD_NAME }, parentNames)
                .build();

        Page<MaterialCategory> categoryPage = materialCategoryRepository.findAll(spec,
                pageRequest(sort, orderAscending, page, size));

        return categoryPage;
    }

    @Override
    public MaterialCategory getCategory(Long categoryId) {
        MaterialCategory category = materialCategoryRepository.findById(categoryId).orElse(null);

        return category;
    }

    @Override
    public MaterialCategory addCategory(Long parentCategoryId, MaterialCategory materialCategory) {
        if (parentCategoryId != null) {
            MaterialCategory parentCategory = Optional.ofNullable(getCategory(parentCategoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", parentCategoryId.toString()));
            materialCategory.setParentCategory(parentCategory);
        }

        MaterialCategory savedEntity = materialCategoryRepository.saveAndFlush(materialCategory);

        return savedEntity;
    }

    @Override
    public MaterialCategory putCategory(Long parentCategoryId, Long categoryId, MaterialCategory putCategory) {
        putCategory.setId(categoryId);

        return addCategory(parentCategoryId, putCategory);
    }

    @Override
    public MaterialCategory patchCategory(Long parentCategoryId, Long categoryId, MaterialCategory updateMaterialCategory) {
        MaterialCategory category = Optional.ofNullable(getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", categoryId.toString()));

        updateMaterialCategory.copyToNullFields(category);

        return addCategory(parentCategoryId, updateMaterialCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        materialCategoryRepository.deleteById(categoryId);
    }

    @Override
    public boolean categoryExists(Long categoryId) {
        return materialCategoryRepository.existsById(categoryId);
    }
}
