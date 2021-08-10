package io.company.brewcraft.dto;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class UpdateEquipmentDto extends BaseDto {

    private String name;

    private EquipmentType type;

    private EquipmentStatus status;

    private QuantityDto maxCapacity;

    private Integer version;

    public UpdateEquipmentDto() {
        super();
    }

    public UpdateEquipmentDto(String name, EquipmentType type, EquipmentStatus status, QuantityDto maxCapacity, Integer version) {
        super();
        this.name = name;
        this.type = type;
        this.status = status;
        this.maxCapacity = maxCapacity;
        this.version = version;
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

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
