package io.company.brewcraft.dto;

import javax.measure.Quantity;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.service.EquipmentTypeAccessor;
import io.company.brewcraft.service.FacilityAccessor;

public interface BaseEquipment extends FacilityAccessor, EquipmentTypeAccessor {
    final String ATTR_NAME = "name";
    final String ATTR_STATUS = "status";
    final String ATTR_MAX_CAPACITY = "maxCapacity";

    String getName();

    void setName(String name);

    EquipmentStatus getStatus();

    void setStatus(EquipmentStatus status);

    Quantity<?> getMaxCapacity();

    void setMaxCapacity(Quantity<?> maxCapacity);
}
