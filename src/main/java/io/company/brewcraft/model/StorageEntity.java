package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="STORAGE")
public class StorageEntity extends BaseEntity {
    public static final String FIELD_ID = "id";
    public static final String FIELD_FACILITY = "facility";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_generator")
    @SequenceGenerator(name="storage_generator", sequenceName = "storage_sequence", allocationSize = 1)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="facility_id", referencedColumnName="id", nullable = false)
    @JsonManagedReference
    private FacilityEntity facility;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private StorageType type;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;
    
    public StorageEntity() {
        
    }
    
    public StorageEntity(Long id, FacilityEntity facility, String name, StorageType type, LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        setId(id);
        setFacility(facility);
        setName(name);
        setType(type);
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

    public StorageType getType() {
        return type;
    }

    public void setType(StorageType type) {
        this.type = type;
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