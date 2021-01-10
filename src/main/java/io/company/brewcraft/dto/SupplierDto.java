package io.company.brewcraft.dto;

import java.util.List;

public class SupplierDto extends BaseDto {
    
    private Long id;
    
    private String name;

    private List<SupplierContactDto> contacts;
    
    private AddressDto address;
    
    private Integer version;
    
    public SupplierDto() {
        super();
    }
    
    public SupplierDto(Long id, String name, List<SupplierContactDto> contacts,
            AddressDto address, Integer version) {
        super();
        this.id = id;
        this.name = name;
        this.contacts = contacts;
        this.address = address;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
