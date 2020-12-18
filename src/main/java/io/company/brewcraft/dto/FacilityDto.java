package io.company.brewcraft.dto;

import java.util.List;

public class FacilityDto extends BaseDto {

    private Long id;
    
    private String name;
    
    private AddressDto address;
    
    private List<FacilityEquipmentDto> equipment;
    
    private List<FacilityStorageDto> storages;
    
    private Integer version;
    
    public FacilityDto() {
        
    }
    
    public FacilityDto(Long id, String name, AddressDto address, List<FacilityEquipmentDto> equipment, List<FacilityStorageDto> storages, Integer version) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.equipment = equipment;
        this.storages = storages;
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