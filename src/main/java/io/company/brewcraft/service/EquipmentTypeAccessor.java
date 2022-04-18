package io.company.brewcraft.service;

import io.company.brewcraft.model.EquipmentType;

public interface EquipmentTypeAccessor {
    final String ATTR_EQUIPMENT_TYPE = "type";

    EquipmentType getType();

    void setType(EquipmentType type);
}
