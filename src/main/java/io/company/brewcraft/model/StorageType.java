package io.company.brewcraft.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StorageType {
    GENERAL("General"),
    COOLER("Cooler"), 
    HOP_STORAGE("Hop Storge"), 
    GRAIN_STORAGE("Grain Storage"), 
    BEER_STORAGE("Beer Storage");
    
    private final String name;
    
    StorageType(String name) {
        this.name = name;
    }
   
    @JsonValue
    public String getStorageTypeName() {
        return this.name;
    }
    
    @Override 
    public String toString() 
    { 
        return name; 
    }

}
