package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddMixtureMaterialPortionDto extends BaseDto {

    @NotNull
    private Long materialLotId;

    @NotNull
    private QuantityDto quantity;

    @NotNull
    private Long mixtureId;

    private LocalDateTime addedAt;

    public AddMixtureMaterialPortionDto() {
        super();
    }

    public AddMixtureMaterialPortionDto(Long materialLotId, QuantityDto quantity, Long mixtureId, LocalDateTime addedAt) {
        this.mixtureId = mixtureId;
        this.materialLotId = materialLotId;
        this.quantity = quantity;
        this.addedAt = addedAt;
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
}