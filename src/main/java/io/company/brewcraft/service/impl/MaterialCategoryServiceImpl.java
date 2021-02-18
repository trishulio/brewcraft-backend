package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateMaterial;
import io.company.brewcraft.dto.UpdateMaterialCategory;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.pojo.MaterialCategory;
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
    public Page<MaterialCategory> getCategories(Set<Long> ids, Set<String> names, Set<Long> parentCategoryIds, Set<String> parentNames, 
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
    public MaterialCategory getCategory(Long categoryId) {
        MaterialCategoryEntity category = materialCategoryRepository.findById(categoryId).orElse(null);    
        
        return materialCategoryMapper.fromEntity(category, new CycleAvoidingMappingContext());
    }

    @Override
    public MaterialCategory addCategory(Long parentCategoryId, MaterialCategory materialCategory) {        
        if (parentCategoryId != null) {
            MaterialCategory parentCategory = Optional.ofNullable(getCategory(parentCategoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", parentCategoryId.toString()));
            materialCategory.setParentCategory(parentCategory);
        }
        
        MaterialCategoryEntity categoryEntity = materialCategoryMapper.toEntity(materialCategory, new CycleAvoidingMappingContext());

        MaterialCategoryEntity savedEntity = materialCategoryRepository.save(categoryEntity);
        
        return materialCategoryMapper.fromEntity(savedEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public MaterialCategory putCategory(Long parentCategoryId, Long categoryId, UpdateMaterialCategory putCategory) {        
        MaterialCategory category = getCategory(categoryId);

        if (category == null) {
            // TODO: setting createdAt is a hack. Need a fix at hibernate level to avoid any hibernate issues.
            category = new MaterialCategory(categoryId, null, null, null, LocalDateTime.now(), null, null);
        }

        category.override(putCategory, getPropertyNames(UpdateMaterial.class));
        
        return addCategory(parentCategoryId, category);
    }

    @Override
    public MaterialCategory patchCategory(Long parentCategoryId, Long categoryId, UpdateMaterialCategory updateMaterialCategory) {                
        MaterialCategory category = Optional.ofNullable(getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", categoryId.toString()));     
        
        category.outerJoin(updateMaterialCategory, getPropertyNames(UpdateMaterialCategory.class));

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
