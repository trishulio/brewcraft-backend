package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class UpdateSupplierContactWithSupplierDto {

    private String firstName;

    private String lastName;

    private String position;

    private String email;

    private String phoneNumber;

    private Long supplierId;

    @NotNull
    private Integer version;

    public UpdateSupplierContactWithSupplierDto() {
    }

    public UpdateSupplierContactWithSupplierDto(String firstName, String lastName, String position, String email,
            String phoneNumber, Long supplierId, Integer version) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.supplierId = supplierId;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
