package io.company.brewcraft.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.joda.money.Money;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class TaxRate extends BaseEntity {
    public static final String FIELD_VALUE = "value";

    @Column(nullable = false, precision = 20, scale = 4)
    private BigDecimal value;

    public TaxRate() {
        super();
    }

    public TaxRate(BigDecimal value) {
        this();
        setValue(value);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Money getTaxAmount(Money money) {
        Money taxAmount = null;

        if (this.value != null && money != null) {
            taxAmount = money.multipliedBy(this.value, RoundingMode.UNNECESSARY);
        }

        return taxAmount;
    }

    @JsonIgnore
    public boolean isSet() {
        return this.value != null && this.value.compareTo(BigDecimal.ZERO) != 0;
    }

    public static boolean isSet(TaxRate taxRate) {
        return taxRate != null && taxRate.isSet();
    }
}
