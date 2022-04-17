package io.company.brewcraft.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;

import org.joda.money.Money;

import io.company.brewcraft.service.MoneyCalculator;
import io.company.brewcraft.service.mapper.MoneyMapper;

@Embeddable
public class TaxAmount extends BaseEntity {
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "pst_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "pst_amount_currency_code", referencedColumnName = "numeric_code"))
    })
    private MoneyEntity pstAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "gst_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "gst_amount_currency_code", referencedColumnName = "numeric_code"))
    })
    private MoneyEntity gstAmount;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "hst_amount"))
    })
    @AssociationOverrides({
        @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "hst_amount_currency_code", referencedColumnName = "numeric_code"))
    })
    private MoneyEntity hstAmount;

    public TaxAmount() {
        super();
    }

    public TaxAmount(Money pstAmount, Money gstAmount, Money hstAmount) {
        this();
        setPstAmount(pstAmount);
        setGstAmount(gstAmount);
        setHstAmount(hstAmount);
    }

    public TaxAmount(Money pstAmount, Money gstAmount) {
        this(pstAmount, gstAmount, null);
    }

    public TaxAmount(Money hstAmount) {
        this(null, null, hstAmount);
    }

    public Money getPstAmount() {
        return MoneyMapper.INSTANCE.fromEntity(pstAmount);
    }

    public Money getGstAmount() {
        return MoneyMapper.INSTANCE.fromEntity(gstAmount);
    }

    public Money getHstAmount() {
        return MoneyMapper.INSTANCE.fromEntity(hstAmount);
    }

    public void setPstAmount(Money pstAmount) {
        this.pstAmount = MoneyMapper.INSTANCE.toEntity(pstAmount);
    }

    public void setGstAmount(Money gstAmount) {
        this.gstAmount = MoneyMapper.INSTANCE.toEntity(gstAmount);
    }

    public void setHstAmount(Money hstAmount) {
        this.hstAmount = MoneyMapper.INSTANCE.toEntity(hstAmount);
    }

    public Money getTotalTaxAmount() {
        List<Money> amounts = new ArrayList<>(3);
        amounts.add(getPstAmount());
        amounts.add(getGstAmount());
        amounts.add(getHstAmount());
        return MoneyCalculator.INSTANCE.totalAmount(amounts);
    }
}
