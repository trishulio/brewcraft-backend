package io.company.brewcraft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.company.brewcraft.model.EquipmentStatus;

public class AddEquipmentDto extends BaseDto {
    @NotBlank
    private String name;

    @NotNull
    private Long typeId;

    @NotNull
    private Long facilityId;

    @NotNull
    private EquipmentStatus status;

    @NotNull
    private QuantityDto maxCapacity;

    public AddEquipmentDto() {
        super();
    }

    public AddEquipmentDto(String name, Long typeId, Long facilityId, EquipmentStatus status, QuantityDto maxCapacity) {
        super();
        this.name = name;
        this.typeId = typeId;
        this.facilityId = facilityId;
        this.status = status;
        this.maxCapacity = maxCapacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
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
