package io.company.brewcraft.dto;

import java.util.List;

public class UpdateFacilityDto extends BaseDto {

    private String name;

    private AddressDto address;

    private String phoneNumber;

    private String faxNumber;

    private List<FacilityEquipmentDto> equipment;

    private List<FacilityStorageDto> storages;

    private Integer version;

    public UpdateFacilityDto() {
        super();
    }

    public UpdateFacilityDto(String name, AddressDto address, String phoneNumber, String faxNumber, List<FacilityEquipmentDto> equipment, List<FacilityStorageDto> storages, Integer version) {
        super();
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
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