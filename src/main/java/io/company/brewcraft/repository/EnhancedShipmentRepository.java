package io.company.brewcraft.repository;

import io.company.brewcraft.model.Shipment;

public interface EnhancedShipmentRepository {
    Shipment save(Long invoiceId, Shipment shipment);
}
