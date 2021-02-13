package io.company.brewcraft.dto;

import java.util.List;

import io.company.brewcraft.pojo.Address;
import io.company.brewcraft.pojo.SupplierContact;

public interface BaseSupplier {
    
    public String getName();

    public void setName(String name);

    public List<SupplierContact> getContacts();

    public void setContacts(List<SupplierContact> contacts);

    public Address getAddress();

    public void setAddress(Address address);

}
