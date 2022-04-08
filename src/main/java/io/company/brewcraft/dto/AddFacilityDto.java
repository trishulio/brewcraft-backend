package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddFacilityDto extends BaseDto {
    @NotBlank
    private String name;

    @NotNull
    private AddressDto address;

    private String phoneNumber;

    private String faxNumber;

    public AddFacilityDto() {
        super();
    }

    public AddFacilityDto(String name, AddressDto address, String phoneNumber, String faxNumber) {
        super();
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
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
}