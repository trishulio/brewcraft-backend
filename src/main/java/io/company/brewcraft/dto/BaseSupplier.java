package io.company.brewcraft.dto;

import java.util.List;

import io.company.brewcraft.model.SupplierAddress;
import io.company.brewcraft.model.SupplierContact;

public interface BaseSupplier {
    
    public String getName();

    public void setName(String name);

    public List<SupplierContact> getContacts();

    public void setContacts(List<SupplierContact> contacts);

    public SupplierAddress getAddress();

    public void setAddress(SupplierAddress address);

}
