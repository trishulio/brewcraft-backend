package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;

public class MoneyService {

    public static Money total(Collection<? extends MoneySupplier> moneySuppliers) {
        Money total = null;
        if (moneySuppliers != null) {
            List<Money> monies = moneySuppliers.stream().filter(i -> i != null && i.getAmount() != null).map(i -> i.getAmount()).collect(Collectors.toList());
            if (monies.size() > 0) {
                total = Money.total(monies);
            }
        }

        return total;
    }
}
