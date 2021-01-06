package io.company.brewcraft.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceItemDto {

    private Long id;
    private QuantityDto quantity;
    private MoneyDto price;
    private MoneyDto amount;
    private String lot;
    private MaterialDto material;
    private Integer version;

    public InvoiceItemDto() {
    }

    public InvoiceItemDto(Long id) {
        this(id, null, null, null, null, null, null);
    }

    public InvoiceItemDto(Long id, QuantityDto quantity, MoneyDto price, MoneyDto amount, String lot, MaterialDto material, Integer version) {
        setId(id);
        setQuantity(quantity);
        setPrice(price);
        setAmount(amount);
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

    public MoneyDto getAmount() {
        return this.amount;
    }
    
    public void setAmount(MoneyDto amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, false);
    }
}
