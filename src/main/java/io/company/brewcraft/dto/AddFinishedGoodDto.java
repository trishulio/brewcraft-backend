package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class AddFinishedGoodDto extends BaseDto {

    @NotNull
    private Long skuId;

    @NotNull
    private List<AddMixturePortionDto> mixturePortions;

    @NotNull
    private List<AddMaterialPortionDto> materialPortions;

    private List<Long> childFinishedGoodIds;

    @NotNull
    private LocalDateTime packagedOn;

    public AddFinishedGoodDto() {
        super();
    }

    public AddFinishedGoodDto(Long skuId, List<AddMixturePortionDto> mixturePortions, List<AddMaterialPortionDto> materialPortions,
            List<Long> childFinishedGoodIds, LocalDateTime packagedOn) {
        this();
        this.skuId = skuId;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.childFinishedGoodIds = childFinishedGoodIds;
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

    public List<Long> getChildFinishedGoodIds() {
        return childFinishedGoodIds;
    }

    public void setChildFinishedGoodIds(List<Long> childFinishedGoodIds) {
        this.childFinishedGoodIds = childFinishedGoodIds;
    }

    public LocalDateTime getPackagedOn() {
        return this.packagedOn;
    }

    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
    }
}
