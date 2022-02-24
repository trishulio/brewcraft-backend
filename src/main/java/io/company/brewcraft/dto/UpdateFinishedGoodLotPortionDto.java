package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;

public class UpdateFinishedGoodLotPortionDto extends BaseDto {

    private Long id;

    private Long finishedGoodLotId;

    private QuantityDto quantity;

    @NotNull
    private Integer version;

    public UpdateFinishedGoodLotPortionDto() {
        super();
    }

    public UpdateFinishedGoodLotPortionDto(Long id, Long finishedGoodLotId, QuantityDto quantity, Integer version) {
        this();
        this.id = id;
        this.finishedGoodLotId = finishedGoodLotId;
        this.quantity = quantity;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}