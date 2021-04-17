package io.company.brewcraft.repository;

import io.company.brewcraft.model.Shipment;

public interface EnhancedShipmentRepository {
    void refresh(Long invoiceId, Shipment shipment);
}
