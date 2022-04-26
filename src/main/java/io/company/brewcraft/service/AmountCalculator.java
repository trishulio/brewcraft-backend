package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.joda.money.Money;

import io.company.brewcraft.model.Amount;
import io.company.brewcraft.model.AmountSupplier;
import io.company.brewcraft.model.Good;
import io.company.brewcraft.model.TaxAmount;
import io.company.brewcraft.model.TaxCalculator;

public class AmountCalculator {
    public static final AmountCalculator INSTANCE = new AmountCalculator(CostCalculator.INSTANCE, TaxCalculator.INSTANCE);

    private CostCalculator costCalculator;
    private TaxCalculator taxCalculator;

    protected AmountCalculator(CostCalculator costCalculator, TaxCalculator taxCalculator) {
        this.costCalculator = costCalculator;
        this.taxCalculator = taxCalculator;
    }

    public Amount getAmount(Good good) {
        Amount total = null;

        Money subTotal = this.costCalculator.getCost(good);
        TaxAmount taxAmount = this.taxCalculator.getTaxAmount(good.getTax(), subTotal);

        if (subTotal != null || taxAmount != null) {
            total = new Amount(subTotal, taxAmount);
        }

        return total;
    }

    public Amount getTotal(Collection<? extends AmountSupplier> amountSuppliers) {
        Amount totalAmount = null;
        if (amountSuppliers != null) {
            Money subTotal = null;
            TaxAmount totalTaxAmount = null;

            List<TaxAmount> taxAmounts = new ArrayList<>(amountSuppliers.size());
            Iterator<Amount> it = amountSuppliers.stream()
                           .filter(Objects::nonNull)
                           .map(AmountSupplier::getAmount)
                           .filter(Objects::nonNull)
                           .iterator();

            while (it.hasNext()) {
                Amount current = it.next();
                taxAmounts.add(current.getTaxAmount());

                if (subTotal == null) {
                    subTotal = current.getSubTotal();
                } else {
                    Money st = current.getSubTotal();
                    if (st != null) {
                        subTotal = subTotal.plus(st);
                    }
                }
            }

            totalTaxAmount = this.taxCalculator.getTaxAmountTotal(taxAmounts);

            totalAmount = new Amount(subTotal, totalTaxAmount);
        }

        return totalAmount;
    }
}
