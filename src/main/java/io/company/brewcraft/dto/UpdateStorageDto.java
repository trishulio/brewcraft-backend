package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.model.StorageType;

public class UpdateStorageDto extends BaseDto {
    @NullOrNotBlank
    private String name;

    private StorageType type;

    @NotNull
    private Integer version;

    public UpdateStorageDto() {
        super();
    }

    public UpdateStorageDto(String name, StorageType type, Integer version) {
        super();
        this.name = name;
        this.type = type;
        this.version = version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}