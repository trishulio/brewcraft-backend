package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;

public class FacilityStorageDto extends BaseDto {

    private Long id;
   
    @NotEmpty
    private String name;
    
    private String type;
    
    private Integer version;
    
    public FacilityStorageDto() {
        
    }
    
    public FacilityStorageDto(Long id, String name, String type, Integer version) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}