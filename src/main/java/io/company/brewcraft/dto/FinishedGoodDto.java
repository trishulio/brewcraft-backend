package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FinishedGoodDto extends BaseDto {

    private Long id;

    private SkuDto sku;

    private List<MixturePortionDto> mixturePortions;

    private List<MaterialPortionDto> materialPortions;

    private Long parentFinishedGoodId;

    private List<FinishedGoodDto> childFinishedGoods;

    private LocalDateTime packagedOn;

    private Integer version;

    public FinishedGoodDto() {
        super();
    }

    public FinishedGoodDto(Long id) {
        this();
        this.id = id;
    }

    public FinishedGoodDto(Long id, SkuDto sku, List<MixturePortionDto> mixturePortions, List<MaterialPortionDto> materialPortions,
            Long parentFinishedGoodId, List<FinishedGoodDto> childFinishedGoods, LocalDateTime packagedOn, Integer version) {
        this(id);
        this.sku = sku;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.parentFinishedGoodId = parentFinishedGoodId;
        this.childFinishedGoods = childFinishedGoods;
        this.packagedOn = packagedOn;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SkuDto getSku() {
        return sku;
    }

    public void setSku(SkuDto sku) {
        this.sku = sku;
    }

    public List<MixturePortionDto> getMixturePortions() {
        return mixturePortions;
    }

    public void setMixturePortions(List<MixturePortionDto> mixturePortions) {
        this.mixturePortions = mixturePortions;
    }

    public List<MaterialPortionDto> getMaterialPortions() {
        return materialPortions;
    }

    public void setMaterialPortions(List<MaterialPortionDto> materialPortions) {
        this.materialPortions = materialPortions;
    }

    public Long getParentFinishedGoodId() {
        return parentFinishedGoodId;
    }

    public void setParentFinishedGoodId(Long parentFinishedGoodId) {
        this.parentFinishedGoodId = parentFinishedGoodId;
    }

    public List<FinishedGoodDto> getChildFinishedGoods() {
        return childFinishedGoods;
    }

    public void setChildFinishedGoods(List<FinishedGoodDto> childFinishedGoods) {
        this.childFinishedGoods = childFinishedGoods;
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
