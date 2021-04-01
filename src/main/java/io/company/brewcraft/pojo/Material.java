package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import javax.measure.Unit;

import io.company.brewcraft.dto.UpdateMaterial;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

public class Material extends BaseModel implements UpdateMaterial, Identified, Audited {
    private Long id;
    
    private String name;
    
    private String description;
    
    private Category category;
        
    private String upc;
    
    private Unit<?> baseQuantityUnit;
    
    private LocalDateTime createdAt;
   
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Material() {
        super();
    }

    public Material(Long id) {
        this();
        this.id = id;
    }

    public Material(Long id, String name, String description, Category category, String upc, Unit<?> baseQuantityUnit, LocalDateTime createdAt,
            LocalDateTime lastUpdated, Integer version) {
        this(id);
        this.name = name;
        this.description = description;
        this.category = category;
        this.upc = upc;
        this.baseQuantityUnit = baseQuantityUnit;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.version = version;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String getUPC() {
        return upc;
    }

    @Override
    public void setUPC(String upc) {
        this.upc = upc;
    }

    @Override
    public Unit<?> getBaseQuantityUnit() {
        return baseQuantityUnit;
    }

    @Override
    public void setBaseQuantityUnit(Unit<?> baseQuantityUnit) {
        this.baseQuantityUnit = baseQuantityUnit;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
