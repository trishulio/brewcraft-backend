package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.ShipmentStatusAccessor;

public class EnhancedShipmentStatusRepositoryImpl implements EnhancedShipmentStatusRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedShipmentStatusRepositoryImpl.class);
    
    private AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> refresher;

    public EnhancedShipmentStatusRepositoryImpl(AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends ShipmentStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
