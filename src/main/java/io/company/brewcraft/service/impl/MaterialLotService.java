package io.company.brewcraft.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.UpdateService;

public class MaterialLotService extends BaseService implements UpdateService<Long, MaterialLot, BaseMaterialLot<?>, UpdateMaterialLot<?>> {
    private static final Logger log = LoggerFactory.getLogger(MaterialLotService.class);

    private final UpdateService<Long, MaterialLot, BaseMaterialLot<?>, UpdateMaterialLot<?>> updateService;

    public MaterialLotService(UpdateService<Long, MaterialLot, BaseMaterialLot<?>, UpdateMaterialLot<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<MaterialLot> getAddEntities(List<BaseMaterialLot<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<MaterialLot> getPutEntities(List<MaterialLot> existingItems, List<UpdateMaterialLot<?>> updates) {
        return this.updateService.getPutEntities(existingItems, updates);
    }

    @Override
    public List<MaterialLot> getPatchEntities(List<MaterialLot> existingItems, List<UpdateMaterialLot<?>> patches) {
        return this.updateService.getPatchEntities(existingItems, patches);
    }
}
