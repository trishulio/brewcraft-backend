package io.company.brewcraft.model;

import java.time.LocalDateTime;

import io.company.brewcraft.service.MaterialLotAccessor;

public interface BaseMaterialPortion extends MaterialLotAccessor, QuantityAccessor {
    
    LocalDateTime getAddedAt();
    
    void setAddedAt(LocalDateTime addedAt);

}
