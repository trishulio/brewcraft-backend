package io.company.brewcraft.dto;

import javax.measure.Quantity;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.pojo.Facility;

public interface BaseEquipment {
    
    public Facility getFacility();

    public void setFacility(Facility facility);

    public String getName();

    public void setName(String name);

    public EquipmentType getType();

    public void setType(EquipmentType type);

    public EquipmentStatus getStatus();

    public void setStatus(EquipmentStatus status);

    public Quantity<?> getMaxCapacity();

    public void setMaxCapacity(Quantity<?> maxCapacity);
}
