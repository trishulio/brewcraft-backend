package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class UpdateSupplierContactDto {
        
    private String firstName;
    
    private String lastName;
    
    private String position;
    
    private String email;
    
    private String phoneNumber;

    @NotNull
    private Integer version;
    
    public UpdateSupplierContactDto() {
        
    }
    
    public UpdateSupplierContactDto(String firstName, String lastName, String position, String email,
            String phoneNumber, Integer version) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.version = version;
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
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

}
