package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AddFinishedGoodLotDto extends BaseDto {

    @NotNull
    private Long skuId;

    @NotNull
    private List<AddMixturePortionDto> mixturePortions;

    @NotNull
    private List<AddMaterialPortionDto> materialPortions;

    @NotNull
    private List<AddFinishedGoodLotPortionDto> finishedGoodLotPortions;

    @NotNull
    private QuantityDto quantity;

    @NotNull
    private LocalDateTime packagedOn;

    public AddFinishedGoodLotDto() {
        super();
    }

    public AddFinishedGoodLotDto(Long skuId, List<AddMixturePortionDto> mixturePortions, List<AddMaterialPortionDto> materialPortions,
            List<AddFinishedGoodLotPortionDto> finishedGoodLotPortions, QuantityDto quantity, LocalDateTime packagedOn) {
        this();
        this.skuId = skuId;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.finishedGoodLotPortions = finishedGoodLotPortions;
        this.quantity = quantity;
        this.packagedOn = packagedOn;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public List<AddMixturePortionDto> getMixturePortions() {
        return mixturePortions;
    }

    public void setMixturePortions(List<AddMixturePortionDto> mixturePortions) {
        this.mixturePortions = mixturePortions;
    }

    public List<AddMaterialPortionDto> getMaterialPortions() {
        return materialPortions;
    }

    public void setMaterialPortions(List<AddMaterialPortionDto> materialPortions) {
        this.materialPortions = materialPortions;
    }

    public List<AddFinishedGoodLotPortionDto> getFinishedGoodLotPortions() {
        return finishedGoodLotPortions;
    }

    public void setFinishedGoodLotPortions(List<AddFinishedGoodLotPortionDto> finishedGoodLotPortions) {
        this.finishedGoodLotPortions = finishedGoodLotPortions;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getPackagedOn() {
        return this.packagedOn;
    }

    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
    }
}
