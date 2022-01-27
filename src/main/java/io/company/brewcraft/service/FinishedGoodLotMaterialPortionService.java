package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;

public class FinishedGoodLotMaterialPortionService extends BaseService implements UpdateService<Long, FinishedGoodLotMaterialPortion, BaseFinishedGoodLotMaterialPortion<?>, UpdateFinishedGoodLotMaterialPortion<?>> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotMaterialPortionService.class);

    private final UpdateService<Long, FinishedGoodLotMaterialPortion, BaseFinishedGoodLotMaterialPortion<?>, UpdateFinishedGoodLotMaterialPortion<?>> updateService;

    public FinishedGoodLotMaterialPortionService(UpdateService<Long, FinishedGoodLotMaterialPortion, BaseFinishedGoodLotMaterialPortion<?>, UpdateFinishedGoodLotMaterialPortion<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<FinishedGoodLotMaterialPortion> getAddEntities(List<BaseFinishedGoodLotMaterialPortion<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<FinishedGoodLotMaterialPortion> getPutEntities(List<FinishedGoodLotMaterialPortion> existingPortions, List<UpdateFinishedGoodLotMaterialPortion<?>> updates) {
        return this.updateService.getPutEntities(existingPortions, updates);
    }

    @Override
    public List<FinishedGoodLotMaterialPortion> getPatchEntities(List<FinishedGoodLotMaterialPortion> existingPortions, List<UpdateFinishedGoodLotMaterialPortion<?>> patches) {
        return this.updateService.getPatchEntities(existingPortions, patches);
    }
}
