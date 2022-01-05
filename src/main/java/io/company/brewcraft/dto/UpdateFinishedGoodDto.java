package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class UpdateFinishedGoodDto extends BaseDto {

    private Long id;

    private Long skuId;

    private List<UpdateMixturePortionDto> mixturePortions;

    private List<UpdateMaterialPortionDto> materialPortions;

    private LocalDateTime packagedOn;

    @NotNull
    private Integer version;

    public UpdateFinishedGoodDto() {
        super();
    }

    public UpdateFinishedGoodDto(Long id, Long skuId, List<UpdateMixturePortionDto> mixturePortions,
            List<UpdateMaterialPortionDto> materialPortions, LocalDateTime packagedOn, Integer version) {
        this();
        this.id = id;
        this.skuId = skuId;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.packagedOn = packagedOn;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public List<UpdateMixturePortionDto> getMixturePortions() {
        return mixturePortions;
    }

    public void setMixturePortions(List<UpdateMixturePortionDto> mixturePortions) {
        this.mixturePortions = mixturePortions;
    }

    public List<UpdateMaterialPortionDto> getMaterialPortions() {
        return materialPortions;
    }

    public void setMaterialPortions(List<UpdateMaterialPortionDto> materialPortions) {
        this.materialPortions = materialPortions;
    }

    public LocalDateTime getPackagedOn() {
        return this.packagedOn;
    }

    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
