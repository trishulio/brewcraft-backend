package io.company.brewcraft.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;

import org.joda.money.Money;

import io.company.brewcraft.service.mapper.MoneyMapper;

@Embeddable
public class Amount extends BaseEntity {
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "total_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "total_amount_currency_code", referencedColumnName = "numeric_code"))
    })
    private MoneyEntity totalAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "sub_total_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "sub_total_amount_currency_code", referencedColumnName = "numeric_code"))
    })
    private MoneyEntity subTotal;

    @Embedded
    private TaxAmount taxAmount;

    public Amount() {
        super();
    }

    public Amount(Money subTotal) {
        this();
        setSubTotal(subTotal);
    }

    public Amount(Money subTotal, TaxAmount taxAmount) {
        this(subTotal);
        setTaxAmount(taxAmount);
    }

    public Money getTotalAmount() {
        setTotalAmount();
        return MoneyMapper.INSTANCE.fromEntity(totalAmount);
    }

    @PrePersist
    public void setTotalAmount() {
        Money totalAmount = null;

        Money subTotal = getSubTotal();
        TaxAmount taxAmount = getTaxAmount();

        Money totalTaxAmount = null;
        if (taxAmount != null) {
            totalTaxAmount = taxAmount.getTotalTaxAmount();
        }

        if (subTotal != null && totalTaxAmount != null) {
            totalAmount = subTotal.plus(totalTaxAmount);
        }

        this.totalAmount = MoneyMapper.INSTANCE.toEntity(totalAmount);
    }

    public Money getSubTotal() {
        return MoneyMapper.INSTANCE.fromEntity(subTotal);
    }

    public void setSubTotal(Money subTotal) {
        this.subTotal = MoneyMapper.INSTANCE.toEntity(subTotal);
    }

    public TaxAmount getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(TaxAmount taxAmount) {
        this.taxAmount = taxAmount;
    }
}
