package io.company.brewcraft.dto;

import io.company.brewcraft.model.Supplier;

public interface BaseSupplierContact {

    public Supplier getSupplier();

    public void setSupplier(Supplier supplier);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getPosition();

    public void setPosition(String position);

    public String getEmail();

    public void setEmail(String email);

    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

}
