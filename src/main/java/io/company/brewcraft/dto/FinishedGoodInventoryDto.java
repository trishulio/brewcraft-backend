package io.company.brewcraft.dto;

import java.math.BigDecimal;

import io.company.brewcraft.util.SupportedUnits;

public class FinishedGoodInventoryDto extends BaseDto {

    private SkuDto sku;

    private QuantityDto quantity;

    public FinishedGoodInventoryDto() {
        super();
    }

    public FinishedGoodInventoryDto(SkuDto sku, Long quantity) {
        this.sku = sku;
        this.quantity = new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(quantity));
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

    public void setQuantity(Long quantity) {
        this.quantity = new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(quantity));
    }

}
