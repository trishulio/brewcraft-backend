package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SupplierAddressDto {
    
    private Long id;
    
    private String addressLine1;
    
    private String addressLine2;
    
    private String country;
    
    private String province;
    
    private String city;
    
    private String postalCode;

    @Null
    @JsonIgnore
    private LocalDateTime created;
    
    @Null
    @JsonIgnore
    private LocalDateTime lastUpdated;
    
    public SupplierAddressDto() {
        
    }
    
    public SupplierAddressDto(Long id, String addressLine1, String addressLine2, String country, String province,
            String city, String postalCode, @Null LocalDateTime created, @Null LocalDateTime lastUpdated) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.province = province;
        this.city = city;
        this.postalCode = postalCode;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
