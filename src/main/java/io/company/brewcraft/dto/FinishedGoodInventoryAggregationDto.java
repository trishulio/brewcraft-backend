package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class FinishedGoodInventoryAggregationDto extends BaseDto {

    private Long id;

    private SkuDto sku;

    private QuantityDto quantity;

    private LocalDateTime packagedOn;

    public FinishedGoodInventoryAggregationDto() {
        super();
    }

    public FinishedGoodInventoryAggregationDto(Long id) {
        this();
        this.setId(id);
    }

    public FinishedGoodInventoryAggregationDto(Long id, SkuDto sku, QuantityDto quantity, LocalDateTime packagedOn) {
        this(id);
        this.sku = sku;
        this.quantity = quantity;
        this.packagedOn = packagedOn;
    }

    public Long getId() {
        return this.id;
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
