package io.company.brewcraft.pojo;

import io.company.brewcraft.dto.UpdateMaterial;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

import java.time.LocalDateTime;

import javax.measure.Unit;

public class Material extends BaseModel implements UpdateMaterial, Identified, Audited {
    private Long id;
    
    private String name;
    
    private String description;
    
    private MaterialCategory category;
        
    private String upc;
    
    private Unit<?> baseQuantityUnit;
    
    private LocalDateTime createdAt;
   
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Material() {
        super();
    }

    public Material(Long id, String name, String description, MaterialCategory category, String upc, Unit<?> baseQuantityUnit, LocalDateTime createdAt,
            LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        this.category = category;
    }

    public String getUPC() {
        return upc;
    }

    public void setUPC(String upc) {
        this.upc = upc;
    }

    public Unit<?> getBaseQuantityUnit() {
        return baseQuantityUnit;
    }

    public void setBaseQuantityUnit(Unit<?> baseQuantityUnit) {
        this.baseQuantityUnit = baseQuantityUnit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
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
