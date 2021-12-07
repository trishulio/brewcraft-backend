package io.company.brewcraft.dto;

import javax.validation.constraints.NotBlank;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class FacilityEquipmentDto extends BaseDto {

    private Long id;

    @NotBlank
    private String name;

    private EquipmentType type;

    private EquipmentStatus status;

    private QuantityDto maxCapacity;

    private Integer version;

    public FacilityEquipmentDto() {

    }

    public FacilityEquipmentDto(Long id) {
        super();
        this.id = id;
    }

    public FacilityEquipmentDto(Long id, String name, EquipmentType type, EquipmentStatus status,
            QuantityDto maxCapacity, Integer version) {
        this(id);
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
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
