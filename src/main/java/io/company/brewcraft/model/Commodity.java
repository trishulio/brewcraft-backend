package io.company.brewcraft.model;

import javax.measure.Quantity;

import org.joda.money.Money;

public interface Commodity {
    Money getPrice();

    Quantity<?> getQuantity();
}
