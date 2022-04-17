package io.company.brewcraft.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    public TaxAmount total(Collection<TaxAmount> taxAmounts) {
        TaxAmount taxAmount = null;

        if (taxAmounts != null) {
            List<Money> pst = new ArrayList<>(taxAmounts.size());
            List<Money> gst = new ArrayList<>(taxAmounts.size());
            List<Money> hst = new ArrayList<>(taxAmounts.size());

            taxAmounts.stream()
                      .filter(Objects::nonNull)
                      .forEach(taxAmt -> {
                          Money pstAmount = taxAmt.getPstAmount();
                          Money gstAmount = taxAmt.getGstAmount();
                          Money hstAmount = taxAmt.getHstAmount();
                          if (pstAmount != null) {
                              pst.add(pstAmount);
                          }
                          if (gstAmount != null) {
                              gst.add(gstAmount);
                          }
                          if (hstAmount != null) {
                              hst.add(hstAmount);
                          }
                      });

            Money totalPst = null;
            Money totalGst = null;
            Money totalHst = null;
            if (pst.size() > 0) {
                totalPst = Money.total(pst);
            }
            if (gst.size() > 0) {
                totalGst = Money.total(gst);
            }
            if (hst.size() > 0) {
                totalHst = Money.total(hst);
            }

            taxAmount = new TaxAmount(totalPst, totalGst, totalHst);
        }

        return taxAmount;
    }
}
