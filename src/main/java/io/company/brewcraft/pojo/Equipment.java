package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.utils.SupportedUnits;

public class Equipment extends BaseModel implements UpdateEquipment, Identified, Audited {
    
    private Long id;
    
    private Facility facility;
    
    private String name;
    
    private EquipmentType type;
    
    private EquipmentStatus status;
    
    private Quantity<?> maxCapacity;
    
    private Unit<?> displayUnit;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Equipment() {
        super();
    }

    public Equipment(Long id, Facility facility, String name, EquipmentType type, EquipmentStatus status,
            Quantity<?> maxCapacity, Unit<?> displayUnit, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super();
        setId(id);
        setFacility(facility);
        setName(name);
        setType(type);
        setStatus(status);
        setMaxCapacity(maxCapacity);
        setDisplayUnit(displayUnit);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
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
    
    public Quantity<?> getMaxCapacityInDisplayUnit() {
        if (maxCapacity != null && this.getDisplayUnit() != null) {
            Unit displayUnit = this.getDisplayUnit();
            Quantity<?> quantity = (Quantity<?>) maxCapacity;
            Quantity<?> quantityInDisplayUnit = quantity.to(displayUnit);
            
            return quantityInDisplayUnit;
        } else {
            return maxCapacity;
        }
    }

    public void setMaxCapacity(Quantity<?> maxCapacity) throws IllegalArgumentException {
        Quantity<?> maxCapacityInPersistedUnit = null;
        if (maxCapacity != null) {
            if (!isValidUnit(maxCapacity.getUnit())) {
                throw new IllegalArgumentException(String.format("Unit symbol '%s' is not a valid value", maxCapacity.getUnit().getSymbol()));
            } else if (this.displayUnit != null && !maxCapacity.getUnit().isCompatible(this.displayUnit)) {
                throw new IllegalArgumentException(String.format("Max Capacity unit '%s' is not compatabile with display unit '%s'", maxCapacity.getUnit().getSymbol(), this.displayUnit.getSymbol()));
            } 
            
            if (SupportedUnits.DEFAULT_VOLUME.isCompatible(maxCapacity.getUnit())) {
                Quantity<Volume> quantity = (Quantity<Volume>) maxCapacity;
                maxCapacityInPersistedUnit = quantity.to(SupportedUnits.DEFAULT_VOLUME);
            } else {
                //Must be mass if its not Volume
                Quantity<Mass> quantity = (Quantity<Mass>) maxCapacity;
                maxCapacityInPersistedUnit = quantity.to(SupportedUnits.DEFAULT_MASS);
            } 
        } 
        
        this.maxCapacity = maxCapacityInPersistedUnit;
    }
    
    public Unit<?> getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(Unit<?> displayUnit) throws IllegalArgumentException {
        if (displayUnit != null) {    
            if (!isValidUnit(displayUnit)) {
                throw new IllegalArgumentException(String.format("Unit symbol '%s' is not a valid value", displayUnit.getSymbol()));
            } else if (this.maxCapacity != null && !displayUnit.isCompatible(maxCapacity.getUnit())) {
                throw new IllegalArgumentException(String.format("Display unit '%s' is not compatabile with max capacity unit '%s'", displayUnit.getSymbol(), this.maxCapacity.getUnit().getSymbol()));
            }
        }
        this.displayUnit = displayUnit;
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
    
    private boolean isValidUnit(Unit<?> displayUnit) {
        boolean result = false;
        if (SupportedUnits.DEFAULT_VOLUME.isCompatible(displayUnit) || SupportedUnits.DEFAULT_MASS.isCompatible(displayUnit)) {
            result = true;
        }
        return result;
    }
}
