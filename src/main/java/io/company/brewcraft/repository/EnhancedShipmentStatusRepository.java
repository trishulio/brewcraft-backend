package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.ShipmentStatusAccessor;

public interface EnhancedShipmentStatusRepository {
    void refreshAccessors(Collection<? extends ShipmentStatusAccessor> accessors);
}
