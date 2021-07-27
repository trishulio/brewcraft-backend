package io.company.brewcraft.model;

import javax.measure.Quantity;

import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.MixtureAccessor;

public interface BaseMaterialPortion extends MixtureAccessor, MaterialLotAccessor {
    
    Quantity<?> getQuantity();
    
    void setQuantity(Quantity<?> quantity);

}
