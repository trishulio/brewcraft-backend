package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotFinishedGoodLotPortion;

public class FinishedGoodLotFinishedGoodLotPortionService extends BaseService implements UpdateService<Long, FinishedGoodLotFinishedGoodLotPortion, BaseFinishedGoodLotFinishedGoodLotPortion, UpdateFinishedGoodLotFinishedGoodLotPortion> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotMaterialPortionService.class);

    private final UpdateService<Long, FinishedGoodLotFinishedGoodLotPortion, BaseFinishedGoodLotFinishedGoodLotPortion, UpdateFinishedGoodLotFinishedGoodLotPortion> updateService;

    public FinishedGoodLotFinishedGoodLotPortionService(UpdateService<Long, FinishedGoodLotFinishedGoodLotPortion, BaseFinishedGoodLotFinishedGoodLotPortion, UpdateFinishedGoodLotFinishedGoodLotPortion> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<FinishedGoodLotFinishedGoodLotPortion> getAddEntities(List<BaseFinishedGoodLotFinishedGoodLotPortion> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<FinishedGoodLotFinishedGoodLotPortion> getPutEntities(List<FinishedGoodLotFinishedGoodLotPortion> existingPortions, List<UpdateFinishedGoodLotFinishedGoodLotPortion> updates) {
        return this.updateService.getPutEntities(existingPortions, updates);
    }

    @Override
    public List<FinishedGoodLotFinishedGoodLotPortion> getPatchEntities(List<FinishedGoodLotFinishedGoodLotPortion> existingPortions, List<UpdateFinishedGoodLotFinishedGoodLotPortion> patches) {
        return this.updateService.getPatchEntities(existingPortions, patches);
    }
}
