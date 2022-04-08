package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class AddMixturePortionDto extends BaseDto {
    @NotNull
    private Long mixtureId;

    @NotNull
    private QuantityDto quantity;

    public AddMixturePortionDto() {
        super();
    }

    public AddMixturePortionDto(Long mixtureId, QuantityDto quantity) {
        this();
        this.mixtureId = mixtureId;
        this.quantity = quantity;
    }

    public Long getMixtureId() {
        return mixtureId;
    }

    public void setMixtureId(Long mixtureId) {
        this.mixtureId = mixtureId;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }
}