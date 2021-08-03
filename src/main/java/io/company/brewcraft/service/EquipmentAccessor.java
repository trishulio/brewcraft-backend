package io.company.brewcraft.service;

import io.company.brewcraft.model.Equipment;

public interface EquipmentAccessor {
    final String ATTR_EQUIPMENT = "equipment";

    Equipment getEquipment();
    
    void setEquipment(Equipment equipment);
}
