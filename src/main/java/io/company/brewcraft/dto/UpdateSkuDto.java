package io.company.brewcraft.dto;

import java.util.List;

public class UpdateSkuDto extends BaseDto {
    
    private String name;

    private String description;
    
    private Long productId;
    
    private List<UpdateSkuMaterialDto> materials;
    
    private QuantityDto quantity;
    
    private Integer version;
    
    public UpdateSkuDto() {
        super();
    }

    public UpdateSkuDto(String name, String description, Long productId, List<UpdateSkuMaterialDto> materials, QuantityDto quantity, Integer version) {
        super();
        this.name = name;
        this.description = description;
        this.productId = productId;
        this.materials = materials;
        this.quantity = quantity;
        this.version = version;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
