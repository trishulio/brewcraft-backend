package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FinishedGoodLotDto extends BaseDto {

    private Long id;

    private SkuDto sku;

    private List<MixturePortionDto> mixturePortions;

    private List<MaterialPortionDto> materialPortions;

    private List<FinishedGoodLotPortionDto> finishedGoodLotPortions;

    private QuantityDto quantity;

    private LocalDateTime packagedOn;

    private Integer version;

    public FinishedGoodLotDto() {
        super();
    }

    public FinishedGoodLotDto(Long id) {
        this();
        this.id = id;
    }

    public FinishedGoodLotDto(Long id, SkuDto sku, List<MixturePortionDto> mixturePortions, List<MaterialPortionDto> materialPortions,
            List<FinishedGoodLotPortionDto> finishedGoodLotPortions, QuantityDto quantity, LocalDateTime packagedOn, Integer version) {
        this(id);
        this.sku = sku;
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

    public List<FinishedGoodLotPortionDto> getFinishedGoodLotPortions() {
        return finishedGoodLotPortions;
    }

    public void setFinishedGoodLotPortions(List<FinishedGoodLotPortionDto> finishedGoodLotPortions) {
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
