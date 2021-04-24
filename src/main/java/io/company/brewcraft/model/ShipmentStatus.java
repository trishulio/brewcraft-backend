package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "shipment_status")
@Table
public class ShipmentStatus extends BaseModel implements BaseShipmentStatus, UpdateShipmentStatus, Identified<String>, Audited {
    public static final String FIELD_NAME = "name";
    
    public static final String DEFAULT_STATUS = "DELIVERED";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipment_status_generator")
    @SequenceGenerator(name = "shipment_status_generator", sequenceName = "shipment_status_sequence", allocationSize = 1)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Version
    private Integer version;

    public ShipmentStatus() {
    }

    public ShipmentStatus(String name) {
        this();
        setName(name);
    }


    @Override
    public String getId() {
        return getName();
    }

    @Override
    public void setId(String id) {
        setName(id);
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

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
