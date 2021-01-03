package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class AddSupplierContactDto {
    @NotNull    
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @NotNull
    private String position;
  
    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;
    
    public AddSupplierContactDto() {
        super();
    }
    
    public AddSupplierContactDto(String firstName, String lastName, String position, String email,
            String phoneNumber) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
}
