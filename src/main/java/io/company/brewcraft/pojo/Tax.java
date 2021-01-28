package io.company.brewcraft.pojo;

import java.util.Collection;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;

public class Tax extends BaseModel implements MoneySupplier {
    private Money amount;

    public Tax() {
    }

    public Tax(Money amount) {
        this();
        setAmount(amount);
    }

    @Override
    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public static Tax total(Collection<Tax> taxes) {
        Tax combined = null;
        Money total = MoneyService.total(taxes);
        if (total != null) {
            combined = new Tax(total);
        }
        return combined;
    }
}
