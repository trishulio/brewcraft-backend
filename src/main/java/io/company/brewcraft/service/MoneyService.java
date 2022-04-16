package io.company.brewcraft.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.joda.money.Money;

public class MoneyService {
    public static final MoneyService INSTANCE = new MoneyService();

    protected MoneyService() {
    }

    public Money total(Collection<? extends MoneySupplier> moneySuppliers) {
        Money total = null;
        if (moneySuppliers != null) {
            List<Money> monies = moneySuppliers.stream().filter(i -> i != null && i.getAmount() != null).map(i -> i.getAmount()).toList();
            if (monies.size() > 0) {
                total = Money.total(monies);
            }
        }

        return total;
    }

    public Money total(Money... monies) {
        Money total = null;

        if (monies != null) {
            monies = (Money[]) Arrays.stream(monies)
                           .filter(Objects::nonNull)
                           .toArray();
            total = Money.total(monies);
        }

        return total;
    }
}
