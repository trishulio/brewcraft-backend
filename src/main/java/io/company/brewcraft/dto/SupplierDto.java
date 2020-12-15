package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import io.company.brewcraft.dto.BaseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SupplierDto extends BaseDto {
    
    @Null
    private Long id;
    
    @NotEmpty
    private String name;

    @NotNull
    @Valid
    private List<SupplierContactDto> contacts;
    
    @NotNull
    @Valid
    private SupplierAddressDto address;
    
    @Null
    @JsonIgnore
    private LocalDateTime created;
    
    @Null
    @JsonIgnore
    private LocalDateTime lastUpdated;
    
    @Null
    private Integer version;
    
    public SupplierDto() {
        
    }
    
    public SupplierDto(Long id, String name, List<SupplierContactDto> contacts,
            SupplierAddressDto address, LocalDateTime created, LocalDateTime lastUpdated, Integer version) {
        this.id = id;
        this.name = name;
        this.contacts = contacts;
        this.address = address;
        this.created = created;
        this.lastUpdated = lastUpdated;
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

    public SupplierAddressDto getAddress() {
        return address;
    }

    public void setAddress(SupplierAddressDto address) {
        this.address = address;
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
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
