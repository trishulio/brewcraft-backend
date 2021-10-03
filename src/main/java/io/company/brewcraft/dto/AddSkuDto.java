package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AddSkuDto extends BaseDto {
    
    @NotNull
    private Long productId;
    
    @NotNull
    private List<AddSkuMaterialDto> materials;
    
    private QuantityDto quantity;
    
    public AddSkuDto() {
        super();
    }

    public AddSkuDto(@NotNull Long productId, @NotNull List<AddSkuMaterialDto> materials, QuantityDto quantity) {
        super();
        this.productId = productId;
        this.materials = materials;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<AddSkuMaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<AddSkuMaterialDto> materials) {
        this.materials = materials;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
}
