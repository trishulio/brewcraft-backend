package io.company.brewcraft.dto;

public class UpdateInvoiceItemDto extends BaseDto {
    private Long id;
    private QuantityDto quantity;
    private MoneyDto price;
    private String lot;
    private MaterialDto material;
    private Integer version;

    public UpdateInvoiceItemDto() {
    }

    public UpdateInvoiceItemDto(Long id) {
        this(id, null, null, null, null, null);
    }

    public UpdateInvoiceItemDto(Long id, QuantityDto quantity, MoneyDto price, String lot, MaterialDto material, Integer version) {
        setId(id);
        setQuantity(quantity);
        setPrice(price);
        setLot(lot);
        setMaterial(material);
        setVersion(version);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
