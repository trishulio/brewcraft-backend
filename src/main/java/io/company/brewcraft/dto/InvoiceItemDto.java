package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceItemDto {
    private Long id;
    private String lotNumber;
    private QuantityDto quantity;
    private MoneyDto price;
    private TaxDto tax;
    private MoneyDto amount;
    private String lot;
    private MaterialDto material;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
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

    public MoneyDto getAmount() {
        return amount;
    }

    public void setAmount(MoneyDto amount) {
        this.amount = amount;
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
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
