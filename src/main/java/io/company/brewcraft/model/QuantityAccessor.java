package io.company.brewcraft.model;

import javax.measure.Quantity;

public interface QuantityAccessor {
    final String ATTR_QUANTITY = "quantity";

    Quantity<?> getQuantity();

    void setQuantity(Quantity<?> quantity);
}
