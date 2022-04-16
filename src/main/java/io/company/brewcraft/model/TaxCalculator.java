package io.company.brewcraft.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.money.Money;

public class TaxCalculator {
    public static final TaxCalculator INSTANCE = new TaxCalculator(TaxRateCalculator.INSTANCE);

    private TaxRateCalculator rateCalculator;

    protected TaxCalculator(TaxRateCalculator rateCalculator) {
        this.rateCalculator = rateCalculator;
    }

    public TaxAmount getTaxAmount(TaxedAmount taxedAmount) {
        TaxAmount taxAmount = null;

        if (taxedAmount != null) {
            taxAmount = getTaxAmount(taxedAmount.getTax(), taxedAmount.getAmount());
        }

        return taxAmount;
    }

    public TaxAmount getTaxAmount(Tax tax, Money amount) {
        TaxAmount taxAmount = null;

        if (tax != null && amount != null) {
            Money pst = rateCalculator.getTaxAmount(tax.getPstRate(), amount);
            Money gst = rateCalculator.getTaxAmount(tax.getGstRate(), amount);
            Money hst = rateCalculator.getTaxAmount(tax.getHstRate(), amount);

            taxAmount = new TaxAmount(pst, gst, hst);
        }

        return taxAmount;
    }

    public TaxAmount total(Collection<TaxAmount> taxAmounts) {
        TaxAmount taxAmount = null;

        if (taxAmounts != null) {
            final AtomicInteger i = new AtomicInteger(0);

            List<Money> pst = new ArrayList<>(taxAmounts.size());
            List<Money> gst = new ArrayList<>(taxAmounts.size());
            List<Money> hst = new ArrayList<>(taxAmounts.size());

            taxAmounts.stream()
                      .filter(Objects::nonNull)
                      .forEach(taxAmt -> {
                          pst.add(taxAmt.getPstAmount());
                          gst.add(taxAmt.getGstAmount());
                          hst.add(taxAmt.getHstAmount());
                      });

            Money totalPst = Money.total(pst);
            Money totalGst = Money.total(gst);
            Money totalHst = Money.total(hst);

            taxAmount = new TaxAmount(totalPst, totalGst, totalHst);
        }

        return taxAmount;
    }
}
