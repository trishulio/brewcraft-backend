package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SupplierWithoutContactsDto {

    @Null
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Valid
    private AddressDto address;

    @Null
    @JsonIgnore
    private LocalDateTime created;

    @Null
    @JsonIgnore
    private LocalDateTime lastUpdated;

    @Null
    private Integer version;

    public SupplierWithoutContactsDto() {

    }

    public SupplierWithoutContactsDto(Long id, String name, AddressDto address, Integer version) {
        this.id = id;
        this.name = name;
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
