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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import io.company.brewcraft.service.mapper.QuantityUnitMapper;

@Entity(name = "MATERIAL")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Material extends BaseEntity implements UpdateMaterial, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_UPC = "upc";
    public static final String FIELD_BASE_QUANTITY_UNIT = "baseQuantityUnit";
    public static final String FIELD_IMAGE_SRC = "imageSrc";

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

    @URL
    @Column(name = "image_source")
    private String imageSrc;

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
            Unit<?> baseQuantityUnit, String imageSrc, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setName(name);
        setDescription(description);
        setCategory(category);
        setUPC(upc);
        setBaseQuantityUnit(baseQuantityUnit);
        setImageSrc(imageSrc);
        setCreatedAt(createdAt);
        setLastUpdated(lastUpdated);
        setVersion(version);
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
    public MaterialCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(MaterialCategory category) {
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
        return QuantityUnitMapper.INSTANCE.fromEntity(this.baseQuantityUnit);
    }

    @Override
    public void setBaseQuantityUnit(Unit<?> baseQuantityUnit) {
        this.baseQuantityUnit = QuantityUnitMapper.INSTANCE.toEntity(baseQuantityUnit);
    }

    @Override
    public String getImageSrc() {
        return imageSrc;
    }

    @Override
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
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
