package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity
@Table(name = "sku_material")
public class SkuMaterial extends BaseEntity implements UpdateSkuMaterial<Sku>, Audited, CrudEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sku_material_generator")
    @SequenceGenerator(name = "sku_material_generator", sequenceName = "sku_material_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    @JsonBackReference
    private Sku sku;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @JsonBackReference
    private Material material;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "quantity_value")) })
    @AssociationOverrides({
            @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "quantity_unit", referencedColumnName = "symbol")) })
    private QuantityEntity quantity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public SkuMaterial() {
    }

    public SkuMaterial(Long id) {
        this();
        setId(id);
    }

    public SkuMaterial(Long id, Sku sku, Material material, Quantity<?> quantity, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setSku(sku);
        setMaterial(material);
        setQuantity(quantity);
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
    public Sku getSku() {
        return sku;
    }

    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Quantity<?> getQuantity() {
        return QuantityMapper.INSTANCE.fromEntity(this.quantity);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}