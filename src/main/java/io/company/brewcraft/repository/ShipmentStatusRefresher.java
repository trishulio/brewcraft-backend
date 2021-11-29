package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.ShipmentStatusAccessor;

public class ShipmentStatusRefresher implements Refresher<ShipmentStatus, ShipmentStatusAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ShipmentStatusRefresher.class);

    private final AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> refresher;

    public ShipmentStatusRefresher(AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends ShipmentStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<ShipmentStatus> entities) {
        // TODO
    }
}
