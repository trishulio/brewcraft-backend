package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.service.MeasureAccessor;

public class MeasureRefresher implements EnhancedMeasureRepository {

    private AccessorRefresher<Long, MeasureAccessor, Measure> refresher;

    public MeasureRefresher(AccessorRefresher<Long, MeasureAccessor, Measure> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends MeasureAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
