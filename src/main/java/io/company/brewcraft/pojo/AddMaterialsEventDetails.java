package io.company.brewcraft.pojo;

import java.util.List;

public class AddMaterialsEventDetails implements IAddMaterialsEventDetails {
    
    private List<AddMaterialLot> materialLots;

    public AddMaterialsEventDetails(List<AddMaterialLot> materialLots) {
        this.materialLots = materialLots;
    }

    public List<AddMaterialLot> getMaterialLots() {
        return materialLots;
    }

    public void setMaterialLots(List<AddMaterialLot> materialLots) {
        this.materialLots = materialLots;
    }
    
}
