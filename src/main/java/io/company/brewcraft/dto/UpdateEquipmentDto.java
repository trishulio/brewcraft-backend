package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.model.EquipmentStatus;

public class UpdateEquipmentDto extends BaseDto {

    private Long id;

    @NullOrNotBlank()
    private String name;

    private Long typeId;

    private Long facilityId;

    private EquipmentStatus status;

    private QuantityDto maxCapacity;

    @NotNull
    private Integer version;

    public UpdateEquipmentDto() {
    }

    public UpdateEquipmentDto(Long id) {
        this();
        this.id = id;
    }

    public UpdateEquipmentDto(Long id, String name, Long typeId, Long facilityId, EquipmentStatus status, QuantityDto maxCapacity, Integer version) {
        this(id);
        this.name = name;
        this.typeId = typeId;
        this.facilityId = facilityId;
        this.status = status;
        this.maxCapacity = maxCapacity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
