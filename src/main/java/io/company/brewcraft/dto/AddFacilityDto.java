package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddFacilityDto extends BaseDto {
    
    @NotEmpty
    private String name;
    
    @NotNull
    private AddressDto address;
    
    @NotNull
    private List<FacilityEquipmentDto> equipment;
   
    @NotNull
    private List<FacilityStorageDto> storages;
    
    public AddFacilityDto() {
        
    }
    
    public AddFacilityDto(String name, AddressDto address, List<FacilityEquipmentDto> equipment, List<FacilityStorageDto> storages) {
        this.name = name;
        this.address = address;
        this.equipment = equipment;
        this.storages = storages;
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

    public List<FacilityEquipmentDto> getEquipment() {
        return equipment;
    }
    
    public void setEquipment(List<FacilityEquipmentDto> equipment) {
        this.equipment = equipment;
    }
    
    public List<FacilityStorageDto> getStorages() {
        return storages;
    }
    
    public void setStorages(List<FacilityStorageDto> storages) {
        this.storages = storages;
    }
}