package io.company.brewcraft.dto;

import java.util.List;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.FacilityAddress;
import io.company.brewcraft.model.Storage;

public interface BaseFacility {
    public String getName();

    public void setName(String name);

    public FacilityAddress getAddress();

    public void setAddress(FacilityAddress address);

    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    public String getFaxNumber();

    public void setFaxNumber(String faxNumber);

    public List<Equipment> getEquipment();

    public void setEquipment(List<Equipment> equipment);

    public List<Storage> getStorages();

    public void setStorages(List<Storage> storages);

}
