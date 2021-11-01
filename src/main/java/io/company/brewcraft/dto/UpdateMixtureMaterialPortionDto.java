package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class UpdateMixtureMaterialPortionDto extends BaseDto {

    private Long materialLotId;

    private QuantityDto quantity;

    private Long mixtureId;

    private LocalDateTime addedAt;

    private Integer version;

    public UpdateMixtureMaterialPortionDto() {
        super();
    }

    public UpdateMixtureMaterialPortionDto(Long materialLotId, QuantityDto quantity, Long mixtureId, LocalDateTime addedAt, Integer version) {
        this.materialLotId = materialLotId;
        this.quantity = quantity;
        this.mixtureId = mixtureId;
        this.addedAt = addedAt;
        this.version = version;
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

    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
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