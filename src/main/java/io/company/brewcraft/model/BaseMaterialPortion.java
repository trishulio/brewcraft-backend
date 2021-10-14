package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import io.company.brewcraft.service.MaterialLotAccessor;

public interface BaseMaterialPortion extends MaterialLotAccessor {

    Quantity<?> getQuantity();

    void setQuantity(Quantity<?> quantity);
    
    LocalDateTime getAddedAt();
    
    void setAddedAt(LocalDateTime addedAt);

}
