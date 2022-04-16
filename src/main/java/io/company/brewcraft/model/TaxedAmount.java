package io.company.brewcraft.model;

import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.TaxSupplier;

public interface TaxedAmount extends MoneySupplier, TaxSupplier {
}
