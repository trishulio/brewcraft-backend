package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.measure.Unit;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMaterial;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialCategory;
import io.company.brewcraft.model.UpdateMaterial;
import io.company.brewcraft.repository.MaterialRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.MaterialCategoryService;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class MaterialServiceImpl extends BaseService implements MaterialService {

    private MaterialRepository materialRepository;

    private MaterialCategoryService materialCategoryService;

    private QuantityUnitService quantityUnitService;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialCategoryService materialCategoryService, QuantityUnitService quantityUnitService) {
        this.materialRepository = materialRepository;
        this.materialCategoryService = materialCategoryService;
        this.quantityUnitService = quantityUnitService;
    }

    @Override
    public Page<Material> getMaterials(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Set<Long> categoryIdsAndDescendantIds = new HashSet<Long>();
        if (categoryIds != null || categoryNames != null) {
            Page<MaterialCategory> categories = materialCategoryService.getCategories(categoryIds, categoryNames, null, null, 0, Integer.MAX_VALUE, new TreeSet<>(), true);

            if (categories.getTotalElements() == 0) {
                //If no categories are found then there can be no materials with those categories assigned
                return Page.empty();
            }

            categories.forEach(category -> {
                categoryIdsAndDescendantIds.add(category.getId());
                categoryIdsAndDescendantIds.addAll(category.getDescendantCategoryIds());
            });
        }

        Specification<Material> spec = SpecificationBuilder
                .builder()
                .in(Material.FIELD_ID, ids)
                .in(new String[] { Material.FIELD_CATEGORY, MaterialCategory.FIELD_ID }, categoryIdsAndDescendantIds.isEmpty() ? null : categoryIdsAndDescendantIds)
                .in(new String[] { Material.FIELD_CATEGORY, MaterialCategory.FIELD_NAME }, categoryIdsAndDescendantIds.isEmpty() ? categoryNames : null)
                .build();

        Page<Material> materialPage = materialRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return materialPage;
    }

    @Override
    public Material getMaterial(Long materialId) {
        Material material = materialRepository.findById(materialId).orElse(null);

        return material;
    }

    @Override
    public Material addMaterial(Material material, Long categoryId, String quantityUnitSymbol) {
        mapChildEntites(material, categoryId, quantityUnitSymbol);

        return addMaterial(material);
    }

    @Override
    public Material addMaterial(Material material) {
        Material savedEntity = materialRepository.saveAndFlush(material);

        return savedEntity;
    }

    @Override
    public Material putMaterial(Long materialId, Material material, Long categoryId, String quantityUnitSymbol) {
        mapChildEntites(material, categoryId, quantityUnitSymbol);

        return putMaterial(materialId, material);
    }

    @Override
    public Material putMaterial(Long materialId, Material putMaterial) {
        Material existingMaterial = getMaterial(materialId);

        if (existingMaterial != null && existingMaterial.getVersion() != putMaterial.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(Material.class, existingMaterial.getId());
        }

        if (existingMaterial == null) {
            existingMaterial = putMaterial;
            existingMaterial.setId(materialId);
        } else {
            existingMaterial.override(putMaterial, getPropertyNames(BaseMaterial.class));
        }

        return materialRepository.saveAndFlush(existingMaterial);
    }

    @Override
    public Material patchMaterial(Long materialId, Material materialPatch) {
        Material existingMaterial = Optional.ofNullable(getMaterial(materialId)).orElseThrow(() -> new EntityNotFoundException("Material", materialId.toString()));

        if (existingMaterial.getVersion() != materialPatch.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(Material.class, existingMaterial.getId());
        }

        existingMaterial.outerJoin(materialPatch, getPropertyNames(UpdateMaterial.class));

        return materialRepository.saveAndFlush(existingMaterial);
    }

    @Override
    public Material patchMaterial(Long materialId, Material materialPatch, Long categoryId, String quantityUnitSymbol) {
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

    private void mapChildEntites(Material material, Long categoryId, String quantityUnitSymbol) {
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
