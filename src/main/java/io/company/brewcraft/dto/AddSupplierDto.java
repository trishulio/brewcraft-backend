package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddSupplierDto extends BaseDto {

    @NotBlank
    private String name;

    @NotNull
    @Valid
    private List<SupplierContactDto> contacts;

    @NotNull
    @Valid
    private AddressDto address;

    public AddSupplierDto() {
        super();
    }

    public AddSupplierDto(String name, List<SupplierContactDto> contacts, AddressDto address) {
        super();
        this.name = name;
        this.contacts = contacts;
        this.address = address;
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

}
