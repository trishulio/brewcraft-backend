package io.company.brewcraft.pojo;

import javax.measure.Quantity;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Material;

public class InvoiceItem extends BaseModel {
    private Long id;
    private Quantity<?> quantity;
    private Money price;
    private String lot;
    private Material material;
    private Integer version;

    public InvoiceItem() {
    }

    public InvoiceItem(Long id) {
        this(id, null, null, null, null, null);
    }

    public InvoiceItem(Long id, Quantity<?> quantity, Money price, String lot, Material material, Integer version) {
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

    public Quantity<?> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<?> qty) {
        this.quantity = qty;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Money getAmount() {
        Money amount = null;

        Number qty = this.getQuantity() != null ? this.getQuantity().getValue() : null;
        Money price = this.getPrice() != null ? this.getPrice() : null;

        if (qty != null && price != null) {
            amount = this.getPrice().multipliedBy(qty.longValue());
        }

        return amount;
    }
}
