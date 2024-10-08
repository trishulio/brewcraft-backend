package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class UpdateFacilityDto extends BaseDto {
    @NullOrNotBlank
    private String name;

    private AddressDto address;

    private String phoneNumber;

    private String faxNumber;

    @NotNull
    private Integer version;

    public UpdateFacilityDto() {
        super();
    }

    public UpdateFacilityDto(String name, AddressDto address, String phoneNumber, String faxNumber, Integer version) {
        super();
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}