package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.model.UpdateSkuMaterial;

public class SkuMaterialService extends BaseService implements UpdateService<Long, SkuMaterial, BaseSkuMaterial<?>, UpdateSkuMaterial<?>> {
    private static final Logger log = LoggerFactory.getLogger(SkuMaterialService.class);

    private final UpdateService<Long, SkuMaterial, BaseSkuMaterial<?>, UpdateSkuMaterial<?>> updateService;

    public SkuMaterialService(UpdateService<Long, SkuMaterial, BaseSkuMaterial<?>, UpdateSkuMaterial<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<SkuMaterial> getAddEntities(List<BaseSkuMaterial<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<SkuMaterial> getPutEntities(List<SkuMaterial> existingMaterials, List<UpdateSkuMaterial<?>> updates) {
        return this.updateService.getPutEntities(existingMaterials, updates);
    }

    @Override
    public List<SkuMaterial> getPatchEntities(List<SkuMaterial> existingMaterials, List<UpdateSkuMaterial<?>> patches) {
        return this.updateService.getPatchEntities(existingMaterials, patches);
    }
}
