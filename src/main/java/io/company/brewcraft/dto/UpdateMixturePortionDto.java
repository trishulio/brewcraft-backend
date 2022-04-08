package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class UpdateMixturePortionDto extends BaseDto {
    private Long id;

    private Long mixtureId;

    private QuantityDto quantity;

    @NotNull
    private Integer version;

    public UpdateMixturePortionDto() {
        super();
    }

    public UpdateMixturePortionDto(Long id, Long mixtureId, QuantityDto quantity, Integer version) {
        this();
        this.id = id;
        this.mixtureId = mixtureId;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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