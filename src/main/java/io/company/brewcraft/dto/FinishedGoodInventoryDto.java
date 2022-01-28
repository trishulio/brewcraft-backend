package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FinishedGoodInventoryDto extends BaseDto {

    private Long id;

    private SkuDto sku;

    private List<MixturePortionDto> mixturePortions;

    private List<MaterialPortionDto> materialPortions;

    private List<FinishedGoodLotPortionDto> finishedGoodLotPortions;

    private LocalDateTime packagedOn;

    private QuantityDto quantity;

    public FinishedGoodInventoryDto() {
        super();
    }

    public FinishedGoodInventoryDto(Long id) {
        this();
        this.setId(id);
    }

    public FinishedGoodInventoryDto(Long id, SkuDto sku, List<MixturePortionDto> mixturePortions, List<MaterialPortionDto> materialPortions,
            List<FinishedGoodLotPortionDto> finishedGoodLotPortions, LocalDateTime packagedOn, QuantityDto quantity) {
        this(id);
        this.sku = sku;
        this.mixturePortions = mixturePortions;
        this.materialPortions = materialPortions;
        this.finishedGoodLotPortions = finishedGoodLotPortions;
        this.packagedOn = packagedOn;
        this.quantity = quantity;;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SkuDto getSku() {
        return sku;
    }

    public void setSku(SkuDto sku) {
        this.sku = sku;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getPackagedOn() {
        return packagedOn;
    }

    public void setPackagedOn(LocalDateTime packagedOn) {
        this.packagedOn = packagedOn;
    }

}
