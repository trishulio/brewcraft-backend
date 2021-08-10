package io.company.brewcraft.dto;

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.StorageType;

public interface BaseStorage {

    public Facility getFacility();

    public void setFacility(Facility facility);

    public String getName();

    public void setName(String name);

    public StorageType getType();

    public void setType(StorageType type);

}
