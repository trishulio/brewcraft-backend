package io.company.brewcraft.model;

import java.util.Collection;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;

import org.joda.money.Money;

import io.company.brewcraft.service.MoneyService;
import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.mapper.MoneyMapper;

@Embeddable
public class Tax extends BaseModel implements MoneySupplier {
    public static final String FIELD_AMOUNT = "amount";

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "tax_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "tax_currency_code", referencedColumnName = "numeric_code"))
    })
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
