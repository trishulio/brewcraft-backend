package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Shipment;

public interface EnhancedShipmentRepository {
    void refresh(Collection<Shipment> shipments);
}
