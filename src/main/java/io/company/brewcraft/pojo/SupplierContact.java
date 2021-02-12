package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

public class SupplierContact {
    
    private Long id;
    
    private Supplier supplier;
    
    private String firstName;
    
    private String lastName;
    
    private String position;
    
    private String email;

    private String phoneNumber;
    
    private LocalDateTime created;
    
    private LocalDateTime lastUpdated;
    
    private Integer version;
    
    public SupplierContact() {
        super();
    }

    public SupplierContact(Long id, Supplier supplier, String firstName, String lastName, String position, String email,
            String phoneNumber, LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        super();
        this.id = id;
        this.supplier = supplier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
