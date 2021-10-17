package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import io.company.brewcraft.service.MixtureAccessor;

public interface BaseMixturePortion extends MixtureAccessor {
    
    Quantity<?> getQuantity();

    void setQuantity(Quantity<?> quantity);
    
    LocalDateTime getAddedAt();
    
    void setAddedAt(LocalDateTime addedAt);

}
