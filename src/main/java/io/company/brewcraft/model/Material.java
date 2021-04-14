package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Unit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.mapper.QuantityUnitMapper;

@Entity(name = "MATERIAL")
public class Material extends BaseEntity {
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
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "material_category_id", referencedColumnName = "id")
    private MaterialCategory category;
    
    private String upc;
    
    @ManyToOne(optional = false)
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

    public Material() {
    }

    public Material(Long id) {
        this();
        this.id = id;
    }

    public Material(Long id, String name, String description, MaterialCategory category, String upc, 
            Unit<?> baseQuantityUnit, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setName(name);
        setDescription(description);
        setCategory(category);
        setUPC(upc);
        setBaseQuantityUnit(baseQuantityUnit);
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
        return QuantityUnitMapper.INSTANCE.fromEntity(this.baseQuantityUnit);
    }

    public void setBaseQuantityUnit(Unit<?> baseQuantityUnit) {
        this.baseQuantityUnit = QuantityUnitMapper.INSTANCE.toEntity(baseQuantityUnit);    
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
