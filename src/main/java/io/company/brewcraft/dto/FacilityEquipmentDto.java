package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;

import io.company.brewcraft.model.EquipmentType;

public class FacilityEquipmentDto extends BaseDto {
    
    private Long id;
        
    @NotEmpty
    private String name;
    
    private EquipmentType type;
    
    private String status;
    
    private QuantityDto maxCapacity;
    
    private Integer version;
    
    public FacilityEquipmentDto() {
        
    }

    public FacilityEquipmentDto(Long id, Long facilityId, String name, EquipmentType type, String status, 
            QuantityDto maxCapacity, Integer version) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
