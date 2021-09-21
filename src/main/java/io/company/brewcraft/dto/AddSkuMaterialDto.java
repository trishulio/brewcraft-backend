package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class AddSkuMaterialDto extends BaseDto {
    
    @NotNull
    private Long materialId;
    
    @NotNull
    private QuantityDto quantity;
    
    public AddSkuMaterialDto() {
        super();
    }
    
    public AddSkuMaterialDto(Long materialId) {
        this.materialId = materialId;
    }

    public AddSkuMaterialDto(@NotNull Long materialId, @NotNull QuantityDto quantity) {
        this(materialId);
        this.quantity = quantity;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

}
