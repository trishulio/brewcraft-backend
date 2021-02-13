package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.Identified;

public class Equipment extends BaseModel implements UpdateEquipment, Identified, Audited {
    
    private Long id;
    
    private Facility facility;
    
    private String name;
    
    private EquipmentType type;
    
    private EquipmentStatus status;
    
    private Quantity<?> maxCapacity;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Equipment() {
        super();
    }

    public Equipment(Long id, Facility facility, String name, EquipmentType type, EquipmentStatus status,
            Quantity<?> maxCapacity, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.facility = facility;
        this.name = name;
        this.type = type;
        this.status = status;
        this.maxCapacity = maxCapacity;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
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

    public Quantity<?> getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Quantity<?> maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
