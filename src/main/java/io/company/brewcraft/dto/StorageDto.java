package io.company.brewcraft.dto;

import io.company.brewcraft.model.StorageType;

public class StorageDto extends BaseDto {
    private Long id;

    private FacilityDto facility;

    private String name;

    private StorageType type;

    private Integer version;

    public StorageDto() {
    }

    public StorageDto(Long id) {
        setId(id);
    }

    public StorageDto(Long id, FacilityDto facility, String name, StorageType type, Integer version) {
        this(id);
        this.facility = facility;
        this.name = name;
        this.type = type;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FacilityDto getFacility() {
        return facility;
    }

    public void setFacility(FacilityDto facility) {
        this.facility = facility;
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