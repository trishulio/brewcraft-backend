package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.UpdateStorage;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.StorageType;

public class Storage extends BaseModel implements UpdateStorage, Identified, Audited {
    
    private Long id;
    
    private Facility facility;
    
    private String name;
    
    private StorageType type;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Storage() {
        
    }

    public Storage(Long id, Facility facility, String name, StorageType type, LocalDateTime created,
            LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.facility = facility;
        this.name = name;
        this.type = type;
        this.createdAt = created;
        this.lastUpdated = lastUpdated;
        this.version = version;
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

    public StorageType getType() {
        return type;
    }

    public void setType(StorageType type) {
        this.type = type;
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
   
}
