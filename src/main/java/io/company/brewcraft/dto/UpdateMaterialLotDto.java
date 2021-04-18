package io.company.brewcraft.dto;

public class UpdateMaterialLotDto extends BaseDto {
    private Long id;
    private QuantityDto qty;
    private Long materialId;
    private Integer version;

    public UpdateMaterialLotDto() {
    }

    public UpdateMaterialLotDto(Long id) {
        setId(id);
    }

    public UpdateMaterialLotDto(Long id, QuantityDto qty, Long materialId, Integer version) {
        this(id);
        setQuantity(qty);
        setMaterialId(materialId);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuantityDto getQuantity() {
        return qty;
    }

    public void setQuantity(QuantityDto qty) {
        this.qty = qty;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
