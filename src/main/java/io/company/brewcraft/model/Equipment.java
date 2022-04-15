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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.service.CrudEntity;
import io.company.brewcraft.service.mapper.QuantityMapper;
import io.company.brewcraft.util.QuantityCalculator;

@Entity(name = "equipment")
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Equipment extends BaseEntity implements CrudEntity<Long>, UpdateEquipment, Audited {
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
    private Facility facility;

    private String name;

    @ManyToOne
    @JoinColumn(name="equipment_type_id", referencedColumnName = "id", nullable = false)
    private EquipmentType type;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "max_capacity_value_in_sys_unit"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "unit", joinColumns = @JoinColumn(name = "max_capacity_display_unit_symbol", referencedColumnName = "symbol"))
    })
    private QuantityEntity maxCapacity;

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

    public Equipment(Long id, Facility facility, String name, EquipmentType type, EquipmentStatus status, Quantity<?> maxCapacity, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        this(id);
        setFacility(facility);
        setName(name);
        setType(type);
        setStatus(status);
        setMaxCapacity(maxCapacity);
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
    public Facility getFacility() {
        return facility;
    }

    @Override
    public void setFacility(Facility facility) {
        this.facility = facility;
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
    public EquipmentType getType() {
        return type;
    }

    @Override
    public void setType(EquipmentType type) {
        this.type = type;
    }

    @Override
    public EquipmentStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    @Override
    public Quantity<?> getMaxCapacity() {
        Quantity<?> qty = QuantityMapper.INSTANCE.fromEntity(this.maxCapacity);

        return QuantityCalculator.INSTANCE.fromSystemQuantityValueWithDisplayUnit(qty);
    }

    @Override
    public void setMaxCapacity(Quantity<?> maxCapacity) {
        maxCapacity = QuantityCalculator.INSTANCE.toSystemQuantityValueWithDisplayUnit(maxCapacity);
        this.maxCapacity = QuantityMapper.INSTANCE.toEntity(maxCapacity);
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

    public void setVersion(Integer version) {
        this.version = version;
    }
}