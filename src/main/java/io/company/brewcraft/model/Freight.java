package io.company.brewcraft.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.joda.money.Money;

import io.company.brewcraft.service.mapper.MoneyMapper;

@Embeddable
public class Freight extends BaseModel {
    public static final String FIELD_AMOUNT = "amount";

    @Embedded
    private MoneyEntity amount;

    public Freight() {
    }

    public Freight(Money amount) {
        setAmount(amount);
    }

    public Money getAmount() {
        return MoneyMapper.INSTANCE.fromEntity(this.amount);
    }

    public void setAmount(Money amount) {
        this.amount = MoneyMapper.INSTANCE.toEntity(amount);
    }
}
