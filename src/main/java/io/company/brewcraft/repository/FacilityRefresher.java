package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.service.FacilityAccessor;

public class FacilityRefresher implements Refresher<Facility, FacilityAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FacilityRefresher.class);

    private final AccessorRefresher<Long, FacilityAccessor, Facility> refresher;

    public FacilityRefresher(AccessorRefresher<Long, FacilityAccessor, Facility> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends FacilityAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<Facility> entities) {
        // Not needed at this time
    }
}
