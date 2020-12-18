package io.company.brewcraft.dto;

import java.util.List;

public class UpdateFacilityDto extends BaseDto {

    private String name;
    
    private AddressDto address;
    
    private List<FacilityEquipmentDto> equipment;
    
    private List<FacilityStorageDto> storages;
    
    private Integer version;
    
    public UpdateFacilityDto() {
        
    }
    
    public UpdateFacilityDto(String name, AddressDto address, List<FacilityEquipmentDto> equipment, List<FacilityStorageDto> storages, Integer version) {
        this.name = name;
        this.address = address;
        this.equipment = equipment;
        this.storages = storages;
        this.version = version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}