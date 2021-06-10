package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.company.brewcraft.pojo.AddMaterialLot;

public class AddAddMaterialsEventDetailsDto {
        
    @NotNull
    private List<AddMaterialLot> materialLots;

    public AddAddMaterialsEventDetailsDto(@NotNull List<AddMaterialLot> materialLots) {
        super();
        this.materialLots = materialLots;
    }

    public List<AddMaterialLot> getMaterialLots() {
        return materialLots;
    }

    public void setMaterialLots(List<AddMaterialLot> materialLots) {
        this.materialLots = materialLots;
    }    
}
