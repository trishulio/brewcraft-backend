package io.company.brewcraft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class AddMaterialPortionDto extends BaseDto {
    @NotNull
    private Long materialLotId;

    @NotNull
    private QuantityDto quantity;

    private LocalDateTime addedAt;

    public AddMaterialPortionDto() {
        super();
    }

    public AddMaterialPortionDto(Long materialLotId, QuantityDto quantity, LocalDateTime addedAt) {
        this();
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
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}