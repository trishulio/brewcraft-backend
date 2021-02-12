package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.List;

import io.company.brewcraft.model.BaseModel;

public class Supplier extends BaseModel {
    
    private Long id;

    private String name;

    private List<SupplierContact> contacts;

    private Address address;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;

    private Integer version;
    
    public Supplier() {
        super();
    }

    public Supplier(Long id, String name, List<SupplierContact> contacts, Address address,
            LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.contacts = contacts;
        this.address = address;
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

    public List<SupplierContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<SupplierContact> contacts) {
        this.contacts = contacts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
