package io.company.brewcraft.dto;

import io.company.brewcraft.model.EquipmentStatus;

public class EquipmentDto extends BaseDto {
    private Long id;

    private FacilityDto facility;

    private String name;

    private EquipmentTypeDto type;

    private EquipmentStatus status;

    private QuantityDto maxCapacity;

    private Integer version;

    public EquipmentDto() {
        super();
    }

    public EquipmentDto(Long id) {
        this();
        this.id = id;
    }

    public EquipmentDto(Long id, FacilityDto facility, String name, EquipmentTypeDto type, EquipmentStatus status,
            QuantityDto maxCapacity, Integer version) {
        this(id);
        this.facility = facility;
        this.name = name;
        this.type = type;
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

    public FacilityDto getFacility() {
        return facility;
    }

    public void setFacility(FacilityDto facility) {
        this.facility = facility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentTypeDto getType() {
        return type;
    }

    public void setType(EquipmentTypeDto type) {
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
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
