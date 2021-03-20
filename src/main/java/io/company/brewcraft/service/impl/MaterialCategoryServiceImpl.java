package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateCategory;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.repository.MaterialCategoryRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.MaterialCategoryMapper;

@Transactional
public class MaterialCategoryServiceImpl extends BaseService implements MaterialCategoryService {
    
    private MaterialCategoryMapper materialCategoryMapper = MaterialCategoryMapper.INSTANCE;
    
    private MaterialCategoryRepository materialCategoryRepository;
    
    public MaterialCategoryServiceImpl(MaterialCategoryRepository materialCategoryRepository) {
        this.materialCategoryRepository = materialCategoryRepository;
    }

    @Override
    public Page<Category> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentCategoryIds, Set<String> parentNames, 
            int page, int size, Set<String> sort, boolean orderAscending) {

        Specification<MaterialCategoryEntity> spec = SpecificationBuilder.builder()
                .in(MaterialCategoryEntity.FIELD_ID, ids)
                .in(MaterialCategoryEntity.FIELD_NAME, names)
                .in(new String[] { MaterialCategoryEntity.FIELD_PARENT_CATEGORY, MaterialCategoryEntity.FIELD_ID }, parentCategoryIds)
                .in(new String[] { MaterialCategoryEntity.FIELD_PARENT_CATEGORY, MaterialCategoryEntity.FIELD_NAME }, parentNames)
                .build();

        Page<MaterialCategoryEntity> categoryPage = materialCategoryRepository.findAll(spec,
                pageRequest(sort, orderAscending, page, size));

        return categoryPage.map(materialCategory -> materialCategoryMapper.fromEntity(materialCategory,
                new CycleAvoidingMappingContext()));
    }

    @Override
    public Category getCategory(Long categoryId) {
        MaterialCategoryEntity category = materialCategoryRepository.findById(categoryId).orElse(null);    
        
        return materialCategoryMapper.fromEntity(category, new CycleAvoidingMappingContext());
    }

    @Override
    public Category addCategory(Long parentCategoryId, Category materialCategory) {        
        if (parentCategoryId != null) {
            Category parentCategory = Optional.ofNullable(getCategory(parentCategoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", parentCategoryId.toString()));
            materialCategory.setParentCategory(parentCategory);
        }
        
        MaterialCategoryEntity categoryEntity = materialCategoryMapper.toEntity(materialCategory, new CycleAvoidingMappingContext());

        MaterialCategoryEntity savedEntity = materialCategoryRepository.save(categoryEntity);
        
        return materialCategoryMapper.fromEntity(savedEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Category putCategory(Long parentCategoryId, Long categoryId, UpdateCategory putCategory) {        
        Category category = getCategory(categoryId);

        if (category == null) {
            // TODO: setting createdAt is a hack. Need a fix at hibernate level to avoid any hibernate issues.
            category = new Category(categoryId, null, null, null, LocalDateTime.now(), null, null);
        }

        category.override(putCategory, getPropertyNames(UpdateCategory.class));
        
        return addCategory(parentCategoryId, category);
    }

    @Override
    public Category patchCategory(Long parentCategoryId, Long categoryId, UpdateCategory updateMaterialCategory) {                
        Category category = Optional.ofNullable(getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", categoryId.toString()));     
        
        category.outerJoin(updateMaterialCategory, getPropertyNames(UpdateCategory.class));

        return addCategory(parentCategoryId, category);
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
