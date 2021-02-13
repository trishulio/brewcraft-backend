package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import io.company.brewcraft.dto.UpdateSupplierContact;
import io.company.brewcraft.model.Audited;
import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;

public class SupplierContact extends BaseModel implements UpdateSupplierContact, Identified, Audited {
    
    private Long id;
    
    private Supplier supplier;
    
    private String firstName;
    
    private String lastName;
    
    private String position;
    
    private String email;

    private String phoneNumber;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public SupplierContact() {
        super();
    }

    public SupplierContact(Long id, Supplier supplier, String firstName, String lastName, String position, String email,
            String phoneNumber, LocalDateTime createdAt, LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.supplier = supplier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
