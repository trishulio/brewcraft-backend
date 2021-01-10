package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class AddEquipmentDto extends BaseDto {
    
    @NotEmpty
    private String name;
    
    @NotNull
    private EquipmentType type;
    
    @NotNull
    private EquipmentStatus status;
    
    @NotNull
    private QuantityDto maxCapacity;
        
    public AddEquipmentDto() {
        super();
    }

    public AddEquipmentDto(String name, EquipmentType type, EquipmentStatus status, QuantityDto maxCapacity) {
        super();
        this.name = name;
        this.type = type;
        this.status = status;
        this.maxCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public QuantityDto getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(QuantityDto maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
