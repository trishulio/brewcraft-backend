package io.company.brewcraft.dto;

public class SkuMaterialDto extends BaseDto {

    private Long id;

    private MaterialDto material;

    private QuantityDto quantity;

    private Integer version;

    public SkuMaterialDto() {
        super();
    }

    public SkuMaterialDto(Long id) {
        this();
        this.id = id;
    }

    public SkuMaterialDto(Long id, MaterialDto material, QuantityDto quantity, Integer version) {
        this(id);
        this.material = material;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto materialId) {
        this.material = materialId;
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
