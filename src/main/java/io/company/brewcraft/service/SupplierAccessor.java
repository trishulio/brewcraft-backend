package io.company.brewcraft.service;

import io.company.brewcraft.model.Supplier;

public interface SupplierAccessor {
    final String ATTR_SUPPLIER = "supplier";

    Supplier getSupplier();

    void setSupplier(Supplier supplier);
}
