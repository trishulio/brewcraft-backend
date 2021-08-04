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
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.company.brewcraft.service.mapper.QuantityMapper;

@Entity(name = "MATERIAL_PORTION")
public class MaterialPortion extends BaseEntity implements BaseMaterialPortion, UpdateMaterialPortion, Audited, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String MATERIAL_LOT = "materialLot";
    public static final String FIELD_QUANTITY_VALUE = "quantityValue";
    public static final String FIELD_QUANTITY_UNIT = "quantityUnit";
    public static final String FIELD_MIXTURE = "mixture";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_portion_generator")
    @SequenceGenerator(name = "material_portion_generator", sequenceName = "material_portion_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_lot_id", referencedColumnName = "id", nullable = false)
    private MaterialLot materialLot;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "quantity_value"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "quantity_unit", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mixture_id", referencedColumnName = "id", nullable = false)
    private Mixture mixture;
            
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public MaterialPortion() {
    }
    
    public MaterialPortion(Long id) {
    	this();
        setId(id);
    } 

    public MaterialPortion(Long id, MaterialLot materialLot, Quantity<?> quantity, Mixture mixture, LocalDateTime createdAt, LocalDateTime lastUpdated) {
    	this(id);
        setMaterialLot(materialLot);
        setQuantity(quantity);
        setMixture(mixture);
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
    public MaterialLot getMaterialLot() {
        return materialLot;
    }

    @Override
    public void setMaterialLot(MaterialLot materialLot) {
        this.materialLot = materialLot;
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
    public Mixture getMixture() {
        return mixture;
    }

    @Override
    public void setMixture(Mixture mixture) {
        this.mixture = mixture;
    }
    
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
