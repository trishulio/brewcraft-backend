package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;

public class AddStorageDto extends BaseDto {
   
    @NotEmpty
    private String name;
    
    private String type;
        
    public AddStorageDto() {
        
    }
    
    public AddStorageDto(Long id, String name, String type) {
        this.name = name;
        this.type = type;
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

}