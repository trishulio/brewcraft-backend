package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

@Entity
@Table(name="EQUIPMENT")
public class Equipment extends BaseEntity implements Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_FACILITY = "facility";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_MAX_CAPACITY = "maxCapacity";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_generator")
    @SequenceGenerator(name="equipment_generator", sequenceName = "equipment_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="facility_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Facility facility;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;
    
    @Column(name = "max_capacity_value")
    private BigDecimal maxCapacityValue;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "max_capacity_unit", referencedColumnName = "symbol")
    private UnitEntity maxCapacityUnit;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "max_capacity_display_unit", referencedColumnName = "symbol")
    private UnitEntity maxCapacityDisplayUnit;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public Equipment() {
    }

    public Equipment(Long id) {
        this();
        setId(id);
    }
    
    public Equipment(Long id, Facility facility, String name, EquipmentType type, EquipmentStatus status, BigDecimal maxCapacityValue,
            Unit<?> maxCapacityUnit, Unit<?> maxCapacityDisplayUnit, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setFacility(facility);
        setName(name);
        setType(type);
        setStatus(status);
        setMaxCapacityValue(maxCapacityValue);
        setMaxCapacityUnit(maxCapacityUnit);
        setMaxCapacityDisplayUnit(maxCapacityDisplayUnit);
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
    
    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }
    
    public BigDecimal getMaxCapacityValue() {
        return this.maxCapacityValue;
    }
    
    public void setMaxCapacityValue(BigDecimal value) {
        this.maxCapacityValue = value;
    }
    
    public Unit<?> getMaxCapacityUnit() {
        return QuantityUnitMapper.INSTANCE.fromEntity(maxCapacityUnit);
    }

    public void setMaxCapacityUnit(Unit<?> unit) throws IllegalArgumentException {
        if (unit != null && !isValidUnit(unit)) {    
            throw new IllegalArgumentException(String.format("Unit symbol '%s' is not a valid value", unit.getSymbol()));
        }
        this.maxCapacityUnit = QuantityUnitMapper.INSTANCE.toEntity(unit);
    }
    
    public Unit<?> getMaxCapacityDisplayUnit() {
        return QuantityUnitMapper.INSTANCE.fromEntity(maxCapacityDisplayUnit);
    }

    public void setMaxCapacityDisplayUnit(Unit<?> displayUnit) throws IllegalArgumentException {
        if (displayUnit != null && !isValidUnit(displayUnit)) {    
            throw new IllegalArgumentException(String.format("Unit symbol '%s' is not a valid value", displayUnit.getSymbol()));
        }
        this.maxCapacityDisplayUnit = QuantityUnitMapper.INSTANCE.toEntity(displayUnit);
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Quantity<?> getMaxCapacity() {
        Quantity<?> qty = null;
        if (this.maxCapacityValue != null && this.maxCapacityUnit != null) {
            qty = Quantities.getQuantity(this.maxCapacityValue, QuantityUnitMapper.INSTANCE.fromEntity(maxCapacityUnit));
        }
        return qty;
    }
    
    public Quantity<?> getMaxCapacityInDisplayUnit() {
        Unit displayUnit = this.getMaxCapacityDisplayUnit();

        if (maxCapacityValue != null && displayUnit != null) {
            Quantity<?> quantity = this.getMaxCapacity();
            Quantity<?> quantityInDisplayUnit = quantity.to(displayUnit);

            return quantityInDisplayUnit;
        } else {
            return this.getMaxCapacity();
        }
    }

    @SuppressWarnings("unchecked")
    public void setMaxCapacity(Quantity<?> maxCapacity) throws IllegalArgumentException {
        Quantity<?> maxCapacityInPersistedUnit = null;

        if (maxCapacity != null) {
            if (!isValidUnit(maxCapacity.getUnit())) {
                throw new IllegalArgumentException(String.format("Unit symbol '%s' is not a valid value", maxCapacity.getUnit().getSymbol()));
            } else if (SupportedUnits.DEFAULT_VOLUME.isCompatible(maxCapacity.getUnit())) {
                Quantity<Volume> quantity = (Quantity<Volume>) maxCapacity;
                maxCapacityInPersistedUnit = quantity.to(SupportedUnits.DEFAULT_VOLUME);
            } else {
                //Must be mass if its not Volume
                Quantity<Mass> quantity = (Quantity<Mass>) maxCapacity;
                maxCapacityInPersistedUnit = quantity.to(SupportedUnits.DEFAULT_MASS);
            } 
        } 

        this.maxCapacityValue = maxCapacityInPersistedUnit != null ? (BigDecimal) maxCapacityInPersistedUnit.getValue() : null;
        this.maxCapacityUnit = maxCapacityInPersistedUnit != null ? QuantityUnitMapper.INSTANCE.toEntity(maxCapacityInPersistedUnit.getUnit()) : null;
    }
    
    private boolean isValidUnit(Unit<?> displayUnit) {
        boolean result = false;
        if (SupportedUnits.DEFAULT_VOLUME.isCompatible(displayUnit) || SupportedUnits.DEFAULT_MASS.isCompatible(displayUnit)) {
            result = true;
        }
        return result;
    }
}