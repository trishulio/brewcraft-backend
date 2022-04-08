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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;

@Entity(name = "finished_good_lot_portion")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class FinishedGoodLotPortion extends BaseEntity implements UpdateFinishedGoodLotPortion, CrudEntity<Long>, Audited {
    public static final String FIELD_ID = "id";
    public static final String FIELD_FINISHED_GOOD_LOT = "finishedGoodLot";
    public static final String FIELD_QUANTITY = "quantity";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finished_good_lot_portion_generator")
    @SequenceGenerator(name = "finished_good_lot_portion_generator", sequenceName = "finished_good_lot_portion_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_good_lot_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private FinishedGoodLot finishedGoodLot;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "qty_value_in_sys_unit"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public FinishedGoodLotPortion() {
    }

    public FinishedGoodLotPortion(Long id) {
        this();
        setId(id);
    }

    public FinishedGoodLotPortion(Long id, FinishedGoodLot finishedGoodLot, Quantity<?> quantity, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setFinishedGoodLot(finishedGoodLot);
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
    public FinishedGoodLot getFinishedGoodLot() {
        return finishedGoodLot;
    }

    @Override
    public void setFinishedGoodLot(FinishedGoodLot finishedGoodLot) {
        Quantity<?> lotQty = null;
        if (finishedGoodLot != null) {
            lotQty = finishedGoodLot.getQuantity();
        }
        IncompatibleQuantityUnitException.validateCompatibleQuantities(lotQty, getQuantity());

        this.finishedGoodLot = finishedGoodLot;
    }

    @Override
    public Quantity<?> getQuantity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.quantity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        IncompatibleQuantityUnitException.validateCompatibleQuantities(getLotQuantity(), quantity);

        quantity = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(quantity);
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

    @JsonIgnore
    public Quantity<?> getLotQuantity() {
        Quantity<?> lotQty = null;
        if (this.finishedGoodLot != null) {
            lotQty = finishedGoodLot.getQuantity();
        }

        return lotQty;
    }
}