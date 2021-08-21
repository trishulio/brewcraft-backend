package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class UpdateMaterialPortionDto extends BaseDto {

    private Long mixtureId;
    
    private Long materialLotId;

    private QuantityDto quantity;
    
    private LocalDateTime addedAt;
    
    private Integer version;

    public UpdateMaterialPortionDto(Long mixtureId, Long materialLotId, QuantityDto quantity, LocalDateTime addedAt, Integer version) {
        super();
        this.mixtureId = mixtureId;
        this.materialLotId = materialLotId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.version = version;
    }
    
    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
    }

    public Long getMaterialLotId() {
        return materialLotId;
    }

    public void setMaterialLotId(Long materialLotId) {
        this.materialLotId = materialLotId;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
    
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}