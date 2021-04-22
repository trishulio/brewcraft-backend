package io.company.brewcraft.model;

import java.util.Collection;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.joda.money.Money;

import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.mapper.MoneyMapper;

@Embeddable
public class Tax extends BaseModel implements MoneySupplier {
    public static final String FIELD_AMOUNT = "amount";

    @Embedded
    private MoneyEntity amount;

    public Tax() {
    }

    public Tax(Money amount) {
        this();
        setAmount(amount);
    }

    @Override
    public Money getAmount() {
        return MoneyMapper.INSTANCE.fromEntity(amount);
    }

    public void setAmount(Money amount) {
        this.amount = MoneyMapper.INSTANCE.toEntity(amount);
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
