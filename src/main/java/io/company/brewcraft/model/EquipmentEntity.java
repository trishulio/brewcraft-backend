package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="EQUIPMENT")
public class EquipmentEntity extends BaseEntity {
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
    @JoinColumn(name="facility_id", referencedColumnName="id", nullable = false)
    @JsonManagedReference
    private FacilityEntity facility;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private EquipmentType type;
    
    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;
    
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "qty_id", referencedColumnName = "id")
    private QuantityEntity maxCapacity;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public EquipmentEntity() {
        
    }
    
    public EquipmentEntity(Long id, FacilityEntity facility, String name, EquipmentType type, EquipmentStatus status, QuantityEntity maxCapacity,
            LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        setId(id);
        setFacility(facility);
        setName(name);
        setType(type);
        setStatus(status);
        setMaxCapacity(maxCapacity);
        setCreated(created);
        setLastUpdated(lastUpdated);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public FacilityEntity getFacility() {
        return facility;
    }

    public void setFacility(FacilityEntity facility) {
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

    public QuantityEntity getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(QuantityEntity maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
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