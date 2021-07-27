package io.company.brewcraft.model;

import javax.measure.Quantity;

public interface BaseMaterialPortion {
	
	MaterialLot getMaterialLot();

    void setMaterialLot(MaterialLot materialLot);
    
    Quantity<?> getQuantity();
    
    void setQuantity(Quantity<?> quantity);
    
    Mixture getMixture();
    
    void setMixture(Mixture mixture);

}
