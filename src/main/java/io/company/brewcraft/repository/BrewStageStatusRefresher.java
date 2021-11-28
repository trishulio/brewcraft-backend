package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.service.BrewStageStatusAccessor;

public class BrewStageStatusRefresher implements Refresher<BrewStageStatus, BrewStageStatusAccessor> {

    private AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> refresher;

    public BrewStageStatusRefresher(AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewStageStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<BrewStageStatus> entities) {
        // TODO
    }

}
