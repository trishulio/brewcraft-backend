package io.company.brewcraft.pojo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;

public class Tax extends BaseModel {

    private Money amount;

    public Tax() {

    }

    public Tax(Money amount) {
        this();
        setAmount(amount);
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public static Tax total(Collection<Tax> taxes) {
        // TODO: Should tax be null or resolve to 0?
        List<Money> monies = taxes.stream().map(t -> t == null ? null : t.getAmount()).collect(Collectors.toList());
        Tax tax = new Tax(Money.total(monies));

        return tax;
    }
}
