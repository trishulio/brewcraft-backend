package io.company.brewcraft.model;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.joda.money.Money;

public class TaxCalculator {
    public static final TaxCalculator INSTANCE = new TaxCalculator(TaxRateCalculator.INSTANCE);

    private TaxRateCalculator rateCalculator;

    protected TaxCalculator(TaxRateCalculator rateCalculator) {
        this.rateCalculator = rateCalculator;
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

    public TaxAmount getTaxAmountTotal(Iterable<TaxAmount> taxAmounts) {
        TaxAmount taxAmount = null;

        if (taxAmounts != null) {
            Money pst = null;
            Money gst = null;
            Money hst = null;

            Iterator<TaxAmount> it = StreamSupport.stream(taxAmounts.spliterator(), false)
                  .filter(Objects::nonNull)
                  .iterator();

            while(it.hasNext()) {
                TaxAmount amount = it.next();
                if (pst == null) {
                    pst = amount.getPstAmount();
                } else {
                    Money currentPst = amount.getPstAmount();
                    if (currentPst != null) {
                        pst = pst.plus(currentPst);
                    }
                }

                if (gst == null) {
                    gst = amount.getGstAmount();
                } else {
                    Money currentGst = amount.getGstAmount();
                    if (currentGst != null) {
                        gst = gst.plus(currentGst);
                    }
                }

                if (hst == null) {
                    hst = amount.getHstAmount();
                } else {
                    Money currentHst = amount.getHstAmount();
                    if (currentHst != null) {
                        hst = hst.plus(currentHst);
                    }
                }
            }

            taxAmount = new TaxAmount(pst, gst, hst);
        }

        return taxAmount;
    }
}
