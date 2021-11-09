package io.company.brewcraft.service;

import io.company.brewcraft.model.Tax;

public interface TaxSupplier {
    final String ATTR_TAX = "tax";

    Tax getTax();
}
