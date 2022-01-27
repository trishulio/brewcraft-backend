package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public class UpdateFinishedGoodLotDto extends BaseDto {

    private Long id;

    private Long skuId;

    private List<UpdateMixturePortionDto> mixturePortions;

    private List<UpdateMaterialPortionDto> materialPortions;

    private List<UpdateFinishedGoodLotPortionDto> finishedGoodLotPortions;

    private QuantityDto quantity;

    private LocalDateTime packagedOn;

    @NotNull
    private Integer version;

    public UpdateFinishedGoodLotDto() {
        super();
    }

    public UpdateFinishedGoodLotDto(Long id, Long skuId, List<UpdateMixturePortionDto> mixturePortions, List<UpdateMaterialPortionDto> materialPortions,
            List<UpdateFinishedGoodLotPortionDto> finishedGoodLotPortions, QuantityDto quantity, LocalDateTime packagedOn, Integer version) {
        this();
        this.id = id;
        this.skuId = skuId;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.finishedGoodLotPortions = finishedGoodLotPortions;
        this.quantity = quantity;
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

    public List<UpdateFinishedGoodLotPortionDto> getFinishedGoodLotPortions() {
        return finishedGoodLotPortions;
    }

    public void setFinishedGoodLotPortions(List<UpdateFinishedGoodLotPortionDto> finishedGoodLotPortions) {
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
