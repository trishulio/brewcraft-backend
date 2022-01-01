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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;

@Entity(name = "MIXTURE_PORTION")
@Inheritance(strategy = InheritanceType.JOINED)
public class MixturePortion extends BaseEntity implements UpdateMixturePortion, CrudEntity<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_MIXTURE = "mixture";
    public static final String FIELD_QUANTITY_VALUE = "quantityValue";
    public static final String FIELD_QUANTITY_UNIT = "quantityUnit";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mixture_portion_generator")
    @SequenceGenerator(name = "mixture_portion_generator", sequenceName = "mixture_portion_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mixture_id", referencedColumnName = "id", nullable = false)
    private Mixture mixture;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "qty_value_in_sys_unit"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public MixturePortion() {
    }

    public MixturePortion(Long id) {
        this();
        setId(id);
    }

    public MixturePortion(Long id, Mixture mixture, Quantity<?> quantity, LocalDateTime addedAt, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setMixture(mixture);
        setQuantity(quantity);
        setAddedAt(addedAt);
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
    public Mixture getMixture() {
        return mixture;
    }

    @Override
    public void setMixture(Mixture mixture) {
        Quantity<?> mixtureQty = null;
        if (mixture != null) {
            mixtureQty = mixture.getQuantity();
        }

        IncompatibleQuantityUnitException.validateCompatibleQuantities(mixtureQty, getQuantity());

        this.mixture = mixture;
    }

    @Override
    public Quantity<?> getQuantity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.quantity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        IncompatibleQuantityUnitException.validateCompatibleQuantities(getMixtureQuantity(), quantity);

        quantity = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(quantity);
        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    @Override
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    @Override
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
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

    @JsonIgnore
    public Quantity<?> getMixtureQuantity() {
        Quantity<?> quantity = null;
        if (mixture != null) {
            quantity = this.mixture.getQuantity();
        }

        return quantity;
    }
}
