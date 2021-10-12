package io.company.brewcraft.service;

import org.joda.money.Money;

public interface MoneySupplier {
    final String ATTR_AMOUNT = "amount";

    Money getAmount();
}
