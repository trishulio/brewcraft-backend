package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.service.BrewTaskAccessor;

public class EnhancedBrewTaskRepositoryImpl implements EnhancedBrewTaskRepository {

    private AccessorRefresher<Long, BrewTaskAccessor, BrewTask> refresher;

    public EnhancedBrewTaskRepositoryImpl(AccessorRefresher<Long, BrewTaskAccessor, BrewTask> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewTaskAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
