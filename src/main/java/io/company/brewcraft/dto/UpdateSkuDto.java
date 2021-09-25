package io.company.brewcraft.dto;

import java.util.List;

public class UpdateSkuDto extends BaseDto {
    
    private Long productId;
    
    private List<UpdateSkuMaterialDto> materials;
    
    private QuantityDto quantity;
    
    private Integer version;
    
    public UpdateSkuDto() {
        super();
    }

    public UpdateSkuDto(Long productId, List<UpdateSkuMaterialDto> materials, QuantityDto quantity, Integer version) {
        super();
        this.productId = productId;
        this.materials = materials;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<UpdateSkuMaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<UpdateSkuMaterialDto> materials) {
        this.materials = materials;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
