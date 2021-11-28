package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageStatusAccessor;
import io.company.brewcraft.service.BrewTaskAccessor;

public class BrewStageRefresher implements Refresher<BrewStage, BrewStageAccessor> {
    private static final Logger log = LoggerFactory.getLogger(BrewStageRefresher.class);

    private AccessorRefresher<Long, BrewStageAccessor, BrewStage> refresher;

    private Refresher<BrewTask, BrewTaskAccessor> brewTaskRefresher;

    private Refresher<BrewStageStatus, BrewStageStatusAccessor> brewStageStatusRefresher;

    private Refresher<Brew, BrewAccessor> brewRefresher;

    public BrewStageRefresher(Refresher<BrewTask, BrewTaskAccessor> brewTaskRefresher, Refresher<BrewStageStatus, BrewStageStatusAccessor> brewStageStatusRefresher, Refresher<Brew, BrewAccessor> brewRefresher, AccessorRefresher<Long, BrewStageAccessor, BrewStage> refresher) {
        this.brewStageStatusRefresher = brewStageStatusRefresher;
        this.brewTaskRefresher = brewTaskRefresher;
        this.brewRefresher = brewRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<BrewStage> brewStages) {
        this.brewStageStatusRefresher.refreshAccessors(brewStages);
        this.brewTaskRefresher.refreshAccessors(brewStages);
        this.brewRefresher.refreshAccessors(brewStages);
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewStageAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
