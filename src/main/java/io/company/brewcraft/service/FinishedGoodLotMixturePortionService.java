package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;

public class FinishedGoodLotMixturePortionService extends BaseService implements UpdateService<Long, FinishedGoodLotMixturePortion, BaseFinishedGoodLotMixturePortion<?>, UpdateFinishedGoodLotMixturePortion<?>> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotMixturePortionService.class);

    private final UpdateService<Long, FinishedGoodLotMixturePortion, BaseFinishedGoodLotMixturePortion<?>, UpdateFinishedGoodLotMixturePortion<?>> updateService;

    public FinishedGoodLotMixturePortionService(UpdateService<Long, FinishedGoodLotMixturePortion, BaseFinishedGoodLotMixturePortion<?>, UpdateFinishedGoodLotMixturePortion<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<FinishedGoodLotMixturePortion> getAddEntities(List<BaseFinishedGoodLotMixturePortion<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<FinishedGoodLotMixturePortion> getPutEntities(List<FinishedGoodLotMixturePortion> existingPortions, List<UpdateFinishedGoodLotMixturePortion<?>> updates) {
        return this.updateService.getPutEntities(existingPortions, updates);
    }

    @Override
    public List<FinishedGoodLotMixturePortion> getPatchEntities(List<FinishedGoodLotMixturePortion> existingPortions, List<UpdateFinishedGoodLotMixturePortion<?>> patches) {
        return this.updateService.getPatchEntities(existingPortions, patches);
    }
}
