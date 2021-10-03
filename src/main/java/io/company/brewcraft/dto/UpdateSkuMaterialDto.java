package io.company.brewcraft.dto;

public class UpdateSkuMaterialDto extends BaseDto {
    
    private Long id;
    
    private Long materialId;
    
    private QuantityDto quantity;
    
    private Integer version;
    
    public UpdateSkuMaterialDto() {
        super();
    }
    
    public UpdateSkuMaterialDto(Long id) {
        this();
        this.id = id;
    }

    public UpdateSkuMaterialDto(Long id, Long materialId, QuantityDto quantity, Integer version) {
        this(id);
        this.materialId = materialId;
        this.quantity = quantity;
        this.version = version;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
