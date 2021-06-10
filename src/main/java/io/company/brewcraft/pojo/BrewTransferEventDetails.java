package io.company.brewcraft.pojo;

import javax.measure.Quantity;

public class BrewTransferEventDetails implements IBrewTransferEventDetails {
    
    private Long fromEquipmentId;
    
    private Long toEquipmentId;
    
    private Quantity<?> quantity;  //TODO: This should be materialId and quantity

    public BrewTransferEventDetails(Long fromEquipmentId, Long toEquipmentId, Quantity<?> quantity) {
        this.fromEquipmentId = fromEquipmentId;
        this.toEquipmentId = toEquipmentId;
        this.quantity = quantity;
    }

    public Long getFromEquipmentId() {
        return fromEquipmentId;
    }

    public void setFromEquipmentId(Long fromEquipmentId) {
        this.fromEquipmentId = fromEquipmentId;
    }

    public Long getToEquipmentId() {
        return toEquipmentId;
    }

    public void setToEquipmentId(Long toEquipmentId) {
        this.toEquipmentId = toEquipmentId;
    }

    public Quantity<?> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<?> quantity) {
        this.quantity = quantity;
    }
    
}
