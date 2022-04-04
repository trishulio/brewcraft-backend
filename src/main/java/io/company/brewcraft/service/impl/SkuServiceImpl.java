package io.company.brewcraft.service.impl;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseSku;
import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.model.UpdateSku;
import io.company.brewcraft.model.UpdateSkuMaterial;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.SkuMaterialService;
import io.company.brewcraft.service.SkuService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class SkuServiceImpl implements SkuService {

    private final RepoService<Long, Sku, SkuAccessor> repoService;

    private final UpdateService<Long, Sku, BaseSku<? extends BaseSkuMaterial<?>>, UpdateSku<? extends UpdateSkuMaterial<?>>> updateService;

    private final SkuMaterialService skuMaterialService;

    public SkuServiceImpl(RepoService<Long, Sku, SkuAccessor> repoService, UpdateService<Long, Sku, BaseSku<? extends BaseSkuMaterial<?>>, UpdateSku<? extends UpdateSkuMaterial<?>>> updateService, SkuMaterialService skuMaterialService) {
        this.repoService = repoService;
        this.updateService = updateService;
        this.skuMaterialService = skuMaterialService;
    }

    @Override
    public Page<Sku> getSkus(Set<Long> ids, Set<Long> productIds, Boolean isPackageable, int page, int size, SortedSet<String> sort,
            boolean orderAscending) {
        final Specification<Sku> spec = WhereClauseBuilder.builder()
                                                            .in(Sku.FIELD_ID, ids)
                                                            .in(Sku.FIELD_PRODUCT, productIds)
                                                            .in(Sku.FIELD_IS_PACKAGEABLE, isPackageable != null ? Set.of(isPackageable) : null)
                                                            .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public Sku getSku(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<Sku> addSkus(List<BaseSku<? extends BaseSkuMaterial<?>>> skus) {
        if (skus == null) {
            return null;
        }

        final List<Sku> entities = this.updateService.getAddEntities(skus);

        for (int i = 0; i < skus.size(); i++) {
            final List<? extends BaseSkuMaterial<?>> skuMaterials = skus.get(i).getMaterials();
            final List<SkuMaterial> materials = this.skuMaterialService.getAddEntities((List<BaseSkuMaterial<?>>) skuMaterials);
            entities.get(i).setMaterials(materials);
        }

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<Sku> putSkus(List<UpdateSku<? extends UpdateSkuMaterial<?>>> skus) {
        if (skus == null) {
            return null;
        }

        final List<Sku> existing = this.repoService.getByIds(skus);
        final List<Sku> updated = this.updateService.getPutEntities(existing, skus);

        final int length = Math.max(existing.size(), skus.size());
        for (int i = 0; i < length; i++) {
            final List<SkuMaterial> existingSkuMaterials = i < existing.size() ? existing.get(i).getMaterials() : null;
            final List<? extends UpdateSkuMaterial<?>> updateSkuMaterials = i < skus.size() ? skus.get(i).getMaterials() : null;

            final List<SkuMaterial> updatedSkuMaterials = this.skuMaterialService.getPutEntities(existingSkuMaterials, (List<UpdateSkuMaterial<?>>) updateSkuMaterials);

            updated.get(i).setMaterials(updatedSkuMaterials);
        }

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<Sku> patchSkus(List<UpdateSku<? extends UpdateSkuMaterial<?>>> skus) {
        if (skus == null) {
            return null;
        }

        final List<Sku> existing = this.repoService.getByIds(skus);

        if (existing.size() != skus.size()) {
            final Set<Long> existingIds = existing.stream().map(sku -> sku.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = skus.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find skus with Ids: %s", nonExistingIds));
        }

        final List<Sku> updated = this.updateService.getPatchEntities(existing, skus);

        for (int i = 0; i < existing.size(); i++) {
            final List<SkuMaterial> existingSkuMaterials = existing.get(i).getMaterials();
            final List<? extends UpdateSkuMaterial<?>> updateSkuMaterials = skus.get(i).getMaterials();

            final List<SkuMaterial> updatedSkuMaterials = this.skuMaterialService.getPatchEntities(existingSkuMaterials, (List<UpdateSkuMaterial<?>>) updateSkuMaterials);

            updated.get(i).setMaterials(updatedSkuMaterials);
        }

        return this.repoService.saveAll(updated);
    }

    @Override
    public void deleteSku(Long id) {
        this.repoService.delete(id);
    }

    @Override
    public boolean skuExists(Long id) {
        return this.repoService.exists(id);
    }
}
