package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class UpdateMixturePortionDto extends BaseDto {

    private Long mixtureId;

    private QuantityDto quantity;

    @NotNull
    private Integer version;

    public UpdateMixturePortionDto() {
        super();
    }

    public UpdateMixturePortionDto(Long mixtureId, QuantityDto quantity, Integer version) {
        this();
        this.mixtureId = mixtureId;
        this.quantity = quantity;
        this.version = version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}