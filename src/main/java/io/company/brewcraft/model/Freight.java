package io.company.brewcraft.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;

import org.joda.money.Money;

import io.company.brewcraft.service.mapper.MoneyMapper;

@Embeddable
public class Freight extends BaseModel {
    public static final String FIELD_AMOUNT = "amount";

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "freight_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "freight_currency_code", referencedColumnName = "numeric_code"))
    })
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
