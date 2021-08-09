package io.company.brewcraft.dto;

public class AddMaterialPortionDto extends BaseDto {

    private String materialLotId;

    private QuantityDto quantity;

    public AddMaterialPortionDto(String materialLotId, QuantityDto quantity) {
        super();
        this.materialLotId = materialLotId;
        this.quantity = quantity;
    }

    public String getMaterialLotId() {
        return materialLotId;
    }

    public void setMaterialLotId(String materialLotId) {
        this.materialLotId = materialLotId;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

}