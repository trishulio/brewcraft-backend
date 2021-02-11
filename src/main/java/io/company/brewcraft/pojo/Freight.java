package io.company.brewcraft.pojo;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;

public class Freight extends BaseModel {
    private Money amount;

    public Freight() {
    }

    public Freight(Money amount) {
        setAmount(amount);
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
