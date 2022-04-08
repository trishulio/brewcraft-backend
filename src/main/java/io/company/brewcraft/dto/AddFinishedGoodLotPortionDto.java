package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class AddFinishedGoodLotPortionDto extends BaseDto {
    @NotNull
    private Long finishedGoodLotId;

    @NotNull
    private QuantityDto quantity;

    public AddFinishedGoodLotPortionDto() {
        super();
    }

    public AddFinishedGoodLotPortionDto(Long finishedGoodLotId, QuantityDto quantity) {
        this();
        this.finishedGoodLotId = finishedGoodLotId;
        this.quantity = quantity;
    }

    public Long getFinishedGoodLotId() {
        return finishedGoodLotId;
    }

    public void setFinishedGoodLotId(Long mixtureId) {
        this.finishedGoodLotId = mixtureId;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
}