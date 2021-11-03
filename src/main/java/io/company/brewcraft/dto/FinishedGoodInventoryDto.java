package io.company.brewcraft.dto;

public class FinishedGoodInventoryDto extends BaseDto {
        
    private SkuDto sku;
            
    private Long quantity;
        
    public FinishedGoodInventoryDto() {
        super();
    }

    public FinishedGoodInventoryDto(SkuDto sku, Long quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }
    
    public SkuDto getSku() {
        return sku;
    }

    public void setSku(SkuDto sku) {
        this.sku = sku;
    }

    public Long getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
         
}
