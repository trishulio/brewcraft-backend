package io.company.brewcraft.dto;

public class ShipmentStatusDto extends BaseDto {
    private String name;
    
    public ShipmentStatusDto() {
    }
    
    public ShipmentStatusDto(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
