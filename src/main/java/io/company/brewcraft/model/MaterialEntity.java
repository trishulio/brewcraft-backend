package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "MATERIAL")
public class MaterialEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_UPC = "upc";
    public static final String FIELD_BASE_QUANTITY_UNIT = "baseQuantityUnit";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_generator")
    @SequenceGenerator(name = "material_generator", sequenceName = "material_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    private String description;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "material_category_id", referencedColumnName = "id")
    private MaterialCategoryEntity category;
    
    private String upc;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "unit_symbol", referencedColumnName = "symbol")
    private UnitEntity baseQuantityUnit;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public MaterialEntity() {

    }

    public MaterialEntity(Long id, String name, String description, MaterialCategoryEntity category, String upc, 
            UnitEntity baseQuantityUnit, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
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
    
    public MaterialCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(MaterialCategoryEntity category) {
        this.category = category;
    }

    public String getUPC() {
        return upc;
    }

    public void setUPC(String upc) {
        this.upc = upc;
    }

    public UnitEntity getBaseQuantityUnit() {
        return baseQuantityUnit;
    }

    public void setBaseQuantityUnit(UnitEntity baseQuantityUnit) {
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
