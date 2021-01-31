package io.company.brewcraft.dto;

public class UpdateInvoiceItemDto extends BaseDto {
    private String lotNumber;
    private String description;
    private QuantityDto quantity;
    private MoneyDto price;
    private TaxDto tax;
    private MaterialDto material;
    private Integer version;

    public UpdateInvoiceItemDto() {
    }

    public UpdateInvoiceItemDto(String lotNumber, String description, QuantityDto quantity, MoneyDto price, TaxDto tax, MaterialDto material, Integer version) {
        this();
        setLotNumber(lotNumber);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterial(material);
        setVersion(version);
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
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

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
