package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.service.BrewTaskAccessor;

public class BrewTaskRefresher implements Refresher<BrewTask, BrewTaskAccessor> {
    private final AccessorRefresher<Long, BrewTaskAccessor, BrewTask> refresher;

    public BrewTaskRefresher(AccessorRefresher<Long, BrewTaskAccessor, BrewTask> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewTaskAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<BrewTask> entities) {
        // NOTE: Not needed at this time
    }
}
