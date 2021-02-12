package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.BaseModel;

public class Facility extends BaseModel {
    
    private Long id;
    
    private String name;
    
    private Address address;
    
    private String phoneNumber;
    
    private String faxNumber;
    
    private List<Equipment> equipment;
    
    private List<Storage> storages;
        
    private LocalDateTime created;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public Facility() {
        super();
    }

    public Facility(Long id, String name, Address address, String phoneNumber, String faxNumber,
            List<Equipment> equipment, List<Storage> storages, LocalDateTime created, LocalDateTime lastUpdated,
            Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.equipment = equipment;
        this.storages = storages;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<Storage> getStorages() {
        return storages;
    }

    public void setStorages(List<Storage> storages) {
        this.storages = storages;
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
