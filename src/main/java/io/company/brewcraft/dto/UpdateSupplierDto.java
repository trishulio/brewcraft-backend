package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UpdateSupplierDto {
    @NullOrNotBlank
    private String name;

    @Valid
    private List<SupplierContactDto> contacts;

    @Valid
    private AddressDto address;

    @NotNull
    private Integer version;

    public UpdateSupplierDto() {
        super();
    }

    public UpdateSupplierDto(String name, List<SupplierContactDto> contacts, AddressDto address, Integer version) {
        super();
        this.name = name;
        this.contacts = contacts;
        this.address = address;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SupplierContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<SupplierContactDto> contacts) {
        this.contacts = contacts;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
