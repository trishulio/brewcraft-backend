package io.company.brewcraft.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.company.brewcraft.model.StorageType;

public class AddStorageDto extends BaseDto {
    @NotBlank
    private String name;

    @NotNull
    private StorageType type;

    public AddStorageDto() {
        super();
    }

    public AddStorageDto(String name, StorageType type) {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageType getType() {
        return type;
    }

    public void setType(StorageType type) {
        this.type = type;
    }
}