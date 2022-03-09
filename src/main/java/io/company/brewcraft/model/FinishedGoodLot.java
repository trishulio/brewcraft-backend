package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;

import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.service.CriteriaJoin;
import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;
import io.company.brewcraft.util.SupportedUnits;

@Entity(name = "finished_good_lot")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class FinishedGoodLot extends BaseEntity implements UpdateFinishedGoodLot<FinishedGoodLotMixturePortion, FinishedGoodLotMaterialPortion>, CrudEntity<Long>, Audited {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SKU = "sku";
    public static final String FIELD_MIXTURE_PORTIONS = "mixturePortions";
    public static final String FIELD_MATERIAL_PORTIONS = "materialPortions";
    public static final String FIELD_FINISHED_GOOD_LOT_PORTIONS = "finishedGoodLotPortions";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_PACKAGED_ON = "packagedOn";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finished_good_lot_generator")
    @SequenceGenerator(name = "finished_good_lot_generator", sequenceName = "finished_good_lot_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sku_id", referencedColumnName = "id")
    private Sku sku;

    @OneToMany(mappedBy = "finishedGoodLot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @CriteriaJoin
    private List<FinishedGoodLotMixturePortion> mixturePortions;

    @OneToMany(mappedBy = "finishedGoodLot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @CriteriaJoin
    private List<FinishedGoodLotMaterialPortion> materialPortions;

    @OneToMany(mappedBy = "finishedGoodLotTarget", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @CriteriaJoin
    private List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "qty_value_in_sys_unit"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "display_qty_unit_symbol", referencedColumnName = "symbol"))
    })
    private QuantityEntity quantity;

    @Column(name = "packaged_on")
    private LocalDateTime packagedOn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Version
    private Integer version;

    public FinishedGoodLot() {
    }

    public FinishedGoodLot(Long id) {
        this();
        setId(id);
    }

    public FinishedGoodLot(Long id, Sku sku, List<FinishedGoodLotMixturePortion> mixturePortions, List<FinishedGoodLotMaterialPortion> materialPortions, List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions, Quantity<?> quantity, LocalDateTime packagedOn, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setSku(sku);
        setMixturePortions(mixturePortions);
        setMaterialPortions(materialPortions);
        setFinishedGoodLotPortions(finishedGoodLotPortions);
        setQuantity(quantity);
        setPackagedOn(packagedOn);
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
    public List<FinishedGoodLotMixturePortion> getMixturePortions() {
        return mixturePortions;
    }

    @Override
    public void setMixturePortions(List<FinishedGoodLotMixturePortion> mixturePortions) {
        if (this.mixturePortions != null) {
            if (mixturePortions == null) {
                this.mixturePortions.clear();
            } else {
                this.mixturePortions.stream().filter(mixturePortion -> !mixturePortions.contains(mixturePortion)).collect(Collectors.toList()).forEach(this::removeMixturePortion);
            }
        }

        if (mixturePortions != null) {
            if (this.mixturePortions == null) {
                this.mixturePortions = new ArrayList<>();
                mixturePortions.stream().collect(Collectors.toList()).forEach(this::addMixturePortion);
            } else {
                mixturePortions.stream().filter(mixturePortion -> !this.mixturePortions.contains(mixturePortion)).collect(Collectors.toList()).forEach(this::addMixturePortion);
            }
        }
    }

    public void addMixturePortion(FinishedGoodLotMixturePortion mixturePortion) {
        if (mixturePortion == null) {
            return;
        }

        if (this.mixturePortions == null) {
            this.mixturePortions = new ArrayList<>();
        }

        if (mixturePortion.getFinishedGoodLot() != this) {
            mixturePortion.setFinishedGoodLot(this);
        }

        if (!this.mixturePortions.contains(mixturePortion)) {
            this.mixturePortions.add(mixturePortion);
        }
    }

    public boolean removeMixturePortion(FinishedGoodLotMixturePortion mixturePortion) {
        if (mixturePortion == null || this.mixturePortions == null) {
            return false;
        }

        boolean removed = this.mixturePortions.remove(mixturePortion);

        if (removed) {
            mixturePortion.setFinishedGoodLot(null);
        }

        return removed;
    }

    @Override
    public List<FinishedGoodLotMaterialPortion> getMaterialPortions() {
        return materialPortions;
    }

    @Override
    public void setMaterialPortions(List<FinishedGoodLotMaterialPortion> materialPortions) {
        if (this.materialPortions != null) {
            if (materialPortions == null) {
                this.materialPortions.clear();
            } else {
                this.materialPortions.stream().filter(materialPortion -> !materialPortions.contains(materialPortion)).collect(Collectors.toList()).forEach(this::removeMaterialPortion);
            }
        }

        if (materialPortions != null) {
            if (this.materialPortions == null) {
                this.materialPortions = new ArrayList<>();
                materialPortions.stream().collect(Collectors.toList()).forEach(this::addMaterialPortion);
            } else {
                materialPortions.stream().filter(materialPortion -> !this.materialPortions.contains(materialPortion)).collect(Collectors.toList()).forEach(this::addMaterialPortion);
            }
        }
    }

    public void addMaterialPortion(FinishedGoodLotMaterialPortion materialPortion) {
        if (materialPortion == null) {
            return;
        }

        if (this.materialPortions == null) {
            this.materialPortions = new ArrayList<>();
        }

        if (materialPortion.getFinishedGoodLot() != this) {
            materialPortion.setFinishedGoodLot(this);
        }

        if (!this.materialPortions.contains(materialPortion)) {
            this.materialPortions.add(materialPortion);
        }
    }

    public boolean removeMaterialPortion(FinishedGoodLotMaterialPortion materialPortion) {
        if (materialPortion == null || this.materialPortions == null) {
            return false;
        }

        boolean removed = this.materialPortions.remove(materialPortion);

        if (removed) {
            materialPortion.setFinishedGoodLot(null);
        }

        return removed;
    }

    @Override
    public List<FinishedGoodLotFinishedGoodLotPortion> getFinishedGoodLotPortions() {
        return finishedGoodLotPortions;
    }

    @Override
    public void setFinishedGoodLotPortions(List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions) {
        if (this.finishedGoodLotPortions != null) {
            if (finishedGoodLotPortions == null) {
                this.finishedGoodLotPortions.clear();
            } else {
                this.finishedGoodLotPortions.stream().filter(finishedGoodLotPortion -> !finishedGoodLotPortions.contains(finishedGoodLotPortion)).collect(Collectors.toList()).forEach(this::removeFinishedGoodLotPortion);
            }
        }

        if (finishedGoodLotPortions != null) {
            if (this.finishedGoodLotPortions == null) {
                this.finishedGoodLotPortions = new ArrayList<>();
                finishedGoodLotPortions.stream().collect(Collectors.toList()).forEach(this::addFinishedGoodLotPortion);
            } else {
                finishedGoodLotPortions.stream().filter(finishedGoodLotPortion -> !this.finishedGoodLotPortions.contains(finishedGoodLotPortion)).collect(Collectors.toList()).forEach(this::addFinishedGoodLotPortion);
            }
        }
    }

    public void addFinishedGoodLotPortion(FinishedGoodLotFinishedGoodLotPortion finishedGoodLotPortion) {
        if (finishedGoodLotPortion == null) {
            return;
        }

        FinishedGoodLot portionLot = finishedGoodLotPortion.getFinishedGoodLot();
        if (portionLot != null && portionLot.getId() != null && portionLot.getId() == this.getId() ) {
            throw new IllegalArgumentException("Self referencing error: Cannot add a finishedGoodLotPortion to the same FinishedGoodLot.");
        }

        if (this.finishedGoodLotPortions == null) {
            this.finishedGoodLotPortions = new ArrayList<>();
        }

        if (finishedGoodLotPortion.getFinishedGoodLotTarget() != this) {
            finishedGoodLotPortion.setFinishedGoodLotTarget(this);
        }

        if (!this.finishedGoodLotPortions.contains(finishedGoodLotPortion)) {
            this.finishedGoodLotPortions.add(finishedGoodLotPortion);
        }
    }

    public boolean removeFinishedGoodLotPortion(FinishedGoodLotFinishedGoodLotPortion finishedGoodLotPortion) {
        if (finishedGoodLotPortion == null || this.finishedGoodLotPortions == null) {
            return false;
        }

        boolean removed = this.finishedGoodLotPortions.remove(finishedGoodLotPortion);

        if (removed) {
            finishedGoodLotPortion.setFinishedGoodLotTarget(null);
        }

        return removed;
    }

    @Override
    public Quantity<?> getQuantity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.quantity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    @Override
    @JsonSetter
    public void setQuantity(Quantity<?> quantity) {
        IncompatibleQuantityUnitException.validateExpectedUnit(SupportedUnits.EACH, quantity);
        quantity = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(quantity);

        this.quantity = QuantityMapper.INSTANCE.toEntity(quantity);
    }

    protected void setQuantity(QuantityEntity quantityEntity) {
        this.quantity = quantityEntity;
    }

    @Override
    public LocalDateTime getPackagedOn() {
        return this.packagedOn;
    }

    @Override
    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
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