package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SupplierContactDto {
    
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private String position;
    
    private String email;
    
    private String phoneNumber;

    @Null
    @JsonIgnore
    private LocalDateTime created;
    
    @Null
    @JsonIgnore
    private LocalDateTime lastUpdated;
    
    public SupplierContactDto() {
        
    }
    
    public SupplierContactDto(Long id, String firstName, String lastName, String position, String email,
            String phoneNumber, @Null LocalDateTime created, @Null LocalDateTime lastUpdated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
