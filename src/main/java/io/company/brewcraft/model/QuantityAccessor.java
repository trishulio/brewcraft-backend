package io.company.brewcraft.model;

import javax.measure.Quantity;

public interface QuantityAccessor {
    
    Quantity<?> getQuantity();

    void setQuantity(Quantity<?> quantity);

}
