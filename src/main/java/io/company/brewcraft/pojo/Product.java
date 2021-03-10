package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.UpdateProduct;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

public class Product extends BaseModel implements UpdateProduct, Identified, Audited {
    
    private Long id;
    
    private String name;
    
    private String description;
    
    private Category category;
    
    private ProductMeasures targetMeasures;
        
    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;
    
    private LocalDateTime deletedAt;
        
    private Integer version;
    
    public Product() {
        super();
    }

    public Product(Long id, String name, String description, Category category, ProductMeasures targetMeasures,
            LocalDateTime createdAt, LocalDateTime lastUpdated, LocalDateTime deletedAt, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.targetMeasures = targetMeasures;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.deletedAt = deletedAt;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductMeasures getTargetMeasures() {
        return targetMeasures;
    }

    public void setTargetMeasures(ProductMeasures targetMeasures) {
        this.targetMeasures = targetMeasures;
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
    
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
        
}
