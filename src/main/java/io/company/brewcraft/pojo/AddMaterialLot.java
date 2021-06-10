package io.company.brewcraft.pojo;

import javax.measure.Quantity;

public class AddMaterialLot {
    
    private Long materialLotId;
    
    private Quantity<?> quantity;

    public AddMaterialLot(Long materialLotId, Quantity<?> quantity) {
        super();
        this.materialLotId = materialLotId;
        this.quantity = quantity;
    }

    public Long getMaterialLotId() {
        return materialLotId;
    }

    public void setMaterialLotId(Long materialLotId) {
        this.materialLotId = materialLotId;
    }

    public Quantity<?> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<?> quantity) {
        this.quantity = quantity;
    }
    
}
