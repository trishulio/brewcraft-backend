package io.company.brewcraft.service;

import io.company.brewcraft.model.ShipmentStatus;

public interface ShipmentStatusAccessor {
    final String ATTR_SHIPMENT_STATUS = "shipmentStatus";

    ShipmentStatus getShipmentStatus();

    void setShipmentStatus(ShipmentStatus shipmentStatus);
}
