package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.measure.Unit;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.BaseMaterial;
import io.company.brewcraft.dto.UpdateMaterial;
import io.company.brewcraft.model.MaterialCategoryEntity;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.MaterialCategory;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.MaterialMapper;

@Transactional
public class MaterialServiceImpl extends BaseService implements MaterialService {
    
    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;
    
    private MaterialRepository materialRepository;
    
    private MaterialCategoryService materialCategoryService;
    
    private QuantityUnitService quantityUnitService;
    
    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialCategoryService materialCategoryService, QuantityUnitService quantityUnitService) {
        this.materialRepository = materialRepository;        
        this.materialCategoryService = materialCategoryService;
        this.quantityUnitService = quantityUnitService;
    }

    @Override
    public Page<Material> getMaterials(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, Set<String> sort, boolean orderAscending) {
        
        Set<Long> categoryIdsAndDescendantIds = new HashSet<Long>();
        if (categoryIds != null || categoryNames != null) {           
            Page<MaterialCategory> categories = materialCategoryService.getCategories(categoryIds, categoryNames, null, null, 0, Integer.MAX_VALUE, Set.of("id"), true);            
            
            if (categories.getTotalElements() == 0) {
                //If no categories are found then there can be no materials with those categories assigned
                return Page.empty();
            }
            
            categories.forEach(category -> { 
                categoryIdsAndDescendantIds.add(category.getId());
                categoryIdsAndDescendantIds.addAll(category.getDescendantCategoryIds());
            });
        }
                     
        Specification<MaterialEntity> spec = SpecificationBuilder
                .builder()
                .in(MaterialEntity.FIELD_ID, ids)
                .in(new String[] { MaterialEntity.FIELD_CATEGORY, MaterialCategoryEntity.FIELD_ID }, categoryIdsAndDescendantIds.isEmpty() ? null : categoryIdsAndDescendantIds)
                .in(new String[] { MaterialEntity.FIELD_CATEGORY, MaterialCategoryEntity.FIELD_NAME }, categoryIdsAndDescendantIds.isEmpty() ? categoryNames : null)
                .build();
        
        Page<MaterialEntity> materialPage = materialRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return materialPage.map(material -> materialMapper.fromEntity(material, new CycleAvoidingMappingContext()));
    }

    @Override
    public Material getMaterial(Long materialId) {
        MaterialEntity material = materialRepository.findById(materialId).orElse(null);
        
        return materialMapper.fromEntity(material, new CycleAvoidingMappingContext());
    }
    
    @Override
    public Material addMaterial(Material material, Long categoryId, String quantityUnitSymbol) {               
        mapChildEntites(material, categoryId, quantityUnitSymbol);
       
        return addMaterial(material);
    }

    @Override
    public Material addMaterial(Material material) {                        
        MaterialEntity materialEntity = materialMapper.toEntity(material, new CycleAvoidingMappingContext());
                                        
        MaterialEntity savedEntity = materialRepository.save(materialEntity);
        
        return materialMapper.fromEntity(savedEntity, new CycleAvoidingMappingContext());
    }
    
    @Override
    public Material putMaterial(Long materialId, UpdateMaterial material, Long categoryId, String quantityUnitSymbol) {                
        mapChildEntites(material, categoryId, quantityUnitSymbol);
        
        return putMaterial(materialId, material);
    }

    @Override
    public Material putMaterial(Long materialId, UpdateMaterial material) {                
        Material existing = getMaterial(materialId);

        if (existing == null) {
            // TODO: setting createdAt is a hack. Need a fix at hibernate level to avoid any hibernate issues.
            existing = new Material(materialId, null, null, null, null, null, LocalDateTime.now(), null, null);
        }

        existing.override(material, getPropertyNames(UpdateMaterial.class));
        
        return addMaterial(existing);        
    }
    
    @Override
    public Material patchMaterial(Long materialId, UpdateMaterial materialPatch) {                           
        Material material = Optional.ofNullable(getMaterial(materialId)).orElseThrow(() -> new EntityNotFoundException("Material", materialId.toString()));     
                
        material.outerJoin(materialPatch, getPropertyNames(UpdateMaterial.class));
                
        return addMaterial(material);
    }

    @Override
    public Material patchMaterial(Long materialId, UpdateMaterial materialPatch, Long categoryId, String quantityUnitSymbol) {        
        mapChildEntites(materialPatch, categoryId, quantityUnitSymbol);

        return patchMaterial(materialId, materialPatch);
    }

    @Override
    public void deleteMaterial(Long materialId) {
        materialRepository.deleteById(materialId);                        
    }

    @Override
    public boolean materialExists(Long materialId) {
        return materialRepository.existsById(materialId);
    }
    
    private void mapChildEntites(BaseMaterial material, Long categoryId, String quantityUnitSymbol) {
        if (categoryId != null) {
            MaterialCategory category = Optional.ofNullable(materialCategoryService.getCategory(categoryId)).orElseThrow(() -> new EntityNotFoundException("MaterialCategory", categoryId.toString()));
            material.setCategory(category);
        }
        
        if (quantityUnitSymbol != null) {
            Unit<?> baseQuantityUnit = Optional.ofNullable(quantityUnitService.get(quantityUnitSymbol)).orElseThrow(() -> new EntityNotFoundException("Unit", quantityUnitSymbol));
            material.setBaseQuantityUnit(baseQuantityUnit);
        }
    }
}
