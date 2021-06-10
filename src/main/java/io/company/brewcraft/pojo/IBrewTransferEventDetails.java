package io.company.brewcraft.pojo;

import javax.measure.Quantity;

public interface IBrewTransferEventDetails {
    
    Long getToEquipmentId();
    
    Quantity<?> getQuantity();

}
