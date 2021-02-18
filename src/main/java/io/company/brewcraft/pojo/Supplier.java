package io.company.brewcraft.pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.company.brewcraft.dto.UpdateSupplier;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

public class Supplier extends BaseModel implements UpdateSupplier, Identified, Audited {
    
    private Long id;

    private String name;

    private List<SupplierContact> contacts;

    private Address address;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;

    private Integer version;
    
    public Supplier() {
        super();
    }

    public Supplier(Long id, String name, List<SupplierContact> contacts, Address address,
            LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.contacts = contacts;
        this.address = address;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created) {
        this.createdAt = created;
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
    
    public void addContact(SupplierContact contact) {
        if (this.contacts == null) {
            this.contacts = new ArrayList<>();
        }

        if (contact.getSupplier() != this) {
            contact.setSupplier(this);
        }

        if (!contacts.contains(contact)) {
            this.contacts.add(contact);
        }
    }

}
