package io.company.brewcraft.dto;

public class AddInvoiceItemDto extends BaseDto {
    private String description;
    private QuantityDto quantity;
    private MoneyDto price;
    private TaxDto tax;
    private Long materialId;

    public AddInvoiceItemDto() {
    }
    
    public AddInvoiceItemDto(String description, QuantityDto quantity, MoneyDto price, TaxDto tax, Long materialId) {
        this();
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterialId(materialId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuantityDto getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDto quantity) {
        this.quantity = quantity;
    }

    public MoneyDto getPrice() {
        return price;
    }

    public void setPrice(MoneyDto price) {
        this.price = price;
    }

    public TaxDto getTax() {
        return tax;
    }

    public void setTax(TaxDto tax) {
        this.tax = tax;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
}
