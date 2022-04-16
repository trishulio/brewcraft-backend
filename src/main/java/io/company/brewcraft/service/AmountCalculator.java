package io.company.brewcraft.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.joda.money.Money;

import io.company.brewcraft.model.Amount;
import io.company.brewcraft.model.AmountSupplier;
import io.company.brewcraft.model.TaxAmount;
import io.company.brewcraft.model.TaxCalculator;
import io.company.brewcraft.model.Good;

public class AmountCalculator {
    public static final AmountCalculator INSTANCE = new AmountCalculator(CostCalculator.INSTANCE, TaxCalculator.INSTANCE);

    private CostCalculator costCalculator;
    private TaxCalculator taxCalculator;

    protected AmountCalculator(CostCalculator costCalculator, TaxCalculator taxCalculator) {
        this.costCalculator = costCalculator;
        this.taxCalculator = taxCalculator;
    }

    public Amount getAmount(Good taxaleCommodity) {
        Amount totalAmount = null;

        Money subTotal = this.costCalculator.getCost(taxaleCommodity);
        TaxAmount taxAmount = this.taxCalculator.getTaxAmount(taxaleCommodity.getTax(), subTotal);

        if (subTotal != null || taxAmount != null) {
            totalAmount = new Amount(subTotal, taxAmount);
        }

        return totalAmount;
    }

    public Amount getTotalAmount(Collection<? extends AmountSupplier> amountSuppliers) {
        Money subTotal = null;
        TaxAmount taxAmount = null;

        if (amountSuppliers != null) {
            List<Money> subTotals = new ArrayList<>(amountSuppliers.size());
            List<TaxAmount> taxAmounts = new ArrayList<>(amountSuppliers.size());

            amountSuppliers.stream()
                           .filter(Objects::nonNull)
                           .map(AmountSupplier::getAmount)
                           .filter(Objects::nonNull)
                           .forEach(amount -> {
                               subTotals.add(amount.getSubTotal());
                               taxAmounts.add(amount.getTaxAmount());
                           });
            subTotal = Money.total(subTotals);
            taxAmount = this.taxCalculator.total(taxAmounts);
        }

        return new Amount(subTotal, taxAmount);
    }
}
