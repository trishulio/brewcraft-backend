package io.company.brewcraft.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EquipmentStatus {
    ACTIVE("Active"), 
    INACTIVE("Inactive");
    
    private final String name;
    
    EquipmentStatus(String name) {
        this.name = name;
    }
   
    @JsonValue
    public String getEquipmentStatusName() {
        return this.name;
    }
    
    @Override 
    public String toString() 
    { 
        return name; 
    }

}
