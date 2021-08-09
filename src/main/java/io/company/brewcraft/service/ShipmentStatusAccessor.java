package io.company.brewcraft.service;

import io.company.brewcraft.model.ShipmentStatus;

public interface ShipmentStatusAccessor {
    ShipmentStatus getStatus();

    void setStatus(ShipmentStatus status);
}
