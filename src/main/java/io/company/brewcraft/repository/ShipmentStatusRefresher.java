package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.ShipmentStatusAccessor;

public class ShipmentStatusRefresher implements EnhancedShipmentStatusRepository {
    private static final Logger log = LoggerFactory.getLogger(ShipmentStatusRefresher.class);

    private AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> refresher;

    public ShipmentStatusRefresher(AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends ShipmentStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
