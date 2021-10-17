package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseFinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodMaterialPortion;

public class FinishedGoodMaterialPortionService extends BaseService implements UpdateService<Long, FinishedGoodMaterialPortion, BaseFinishedGoodMaterialPortion<?>, UpdateFinishedGoodMaterialPortion<?>> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodMaterialPortionService.class);

    private final UpdateService<Long, FinishedGoodMaterialPortion, BaseFinishedGoodMaterialPortion<?>, UpdateFinishedGoodMaterialPortion<?>> updateService;

    public FinishedGoodMaterialPortionService(UpdateService<Long, FinishedGoodMaterialPortion, BaseFinishedGoodMaterialPortion<?>, UpdateFinishedGoodMaterialPortion<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<FinishedGoodMaterialPortion> getAddEntities(List<BaseFinishedGoodMaterialPortion<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<FinishedGoodMaterialPortion> getPutEntities(List<FinishedGoodMaterialPortion> existingPortions, List<UpdateFinishedGoodMaterialPortion<?>> updates) {
        return this.updateService.getPutEntities(existingPortions, updates);
    }

    @Override
    public List<FinishedGoodMaterialPortion> getPatchEntities(List<FinishedGoodMaterialPortion> existingPortions, List<UpdateFinishedGoodMaterialPortion<?>> patches) {
        return this.updateService.getPatchEntities(existingPortions, patches);
    }
}
