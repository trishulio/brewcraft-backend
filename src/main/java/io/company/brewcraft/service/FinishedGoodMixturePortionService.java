package io.company.brewcraft.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseFinishedGoodMixturePortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.UpdateFinishedGoodMixturePortion;

public class FinishedGoodMixturePortionService extends BaseService implements UpdateService<Long, FinishedGoodMixturePortion, BaseFinishedGoodMixturePortion<?>, UpdateFinishedGoodMixturePortion<?>> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodMixturePortionService.class);

    private final UpdateService<Long, FinishedGoodMixturePortion, BaseFinishedGoodMixturePortion<?>, UpdateFinishedGoodMixturePortion<?>> updateService;

    public FinishedGoodMixturePortionService(UpdateService<Long, FinishedGoodMixturePortion, BaseFinishedGoodMixturePortion<?>, UpdateFinishedGoodMixturePortion<?>> updateService) {
        this.updateService = updateService;
    }

    @Override
    public List<FinishedGoodMixturePortion> getAddEntities(List<BaseFinishedGoodMixturePortion<?>> additions) {
        return this.updateService.getAddEntities(additions);
    }

    @Override
    public List<FinishedGoodMixturePortion> getPutEntities(List<FinishedGoodMixturePortion> existingPortions, List<UpdateFinishedGoodMixturePortion<?>> updates) {
        return this.updateService.getPutEntities(existingPortions, updates);
    }

    @Override
    public List<FinishedGoodMixturePortion> getPatchEntities(List<FinishedGoodMixturePortion> existingPortions, List<UpdateFinishedGoodMixturePortion<?>> patches) {
        return this.updateService.getPatchEntities(existingPortions, patches);
    }
}
