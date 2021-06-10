package io.company.brewcraft.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AddBrewTransferEventDetailsDto {
        
    @NotNull
    private Long toEquipmentId;
    
    @Valid
    @NotNull
    private QuantityDto quantity;  //TODO: This should be materialId and quantity

    public AddBrewTransferEventDetailsDto(Long toEquipmentId, QuantityDto quantity) {
        this.toEquipmentId = toEquipmentId;
        this.quantity = quantity;
    }

    public Long getToEquipmentId() {
        return toEquipmentId;
    }

    public void setToEquipmentId(Long toEquipmentId) {
        this.toEquipmentId = toEquipmentId;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
    
}
