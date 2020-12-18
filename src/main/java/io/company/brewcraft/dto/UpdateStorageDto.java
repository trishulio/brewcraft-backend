package io.company.brewcraft.dto;

public class UpdateStorageDto extends BaseDto {
   
    private String name;
    
    private String type;
    
    private Integer version;
    
    public UpdateStorageDto() {
        
    }
    
    public UpdateStorageDto(String name, String type, Integer version) {
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