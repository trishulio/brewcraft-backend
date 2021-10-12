package io.company.brewcraft.service;

import io.company.brewcraft.model.Tax;

public interface TaxAccessor extends TaxSupplier {
    void setTax(Tax tax);
}
