package io.company.brewcraft.pojo;

import javax.measure.Quantity;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.service.MoneySupplier;

public class InvoiceItem extends BaseModel implements MoneySupplier, UpdateInvoiceItem, Identified {
    private Long id;
    private String description;
    private Quantity<?> quantity;
    private Money price;
    private Tax tax;
    private Material material;
    private Integer version; // TODO: Do I need a version for items?

    public InvoiceItem() {
    }

    public InvoiceItem(Long id) {
        this();
        setId(id);
    }

    public InvoiceItem(Long id, String description, Quantity<?> quantity, Money price, Tax tax, Material material, Integer version) {
        this(id);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterial(material);
        setVersion(version);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Quantity<?> getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Quantity<?> quantity) {
        this.quantity = quantity;
    }

    @Override
    public Money getPrice() {
        return price;
    }

    @Override
    public void setPrice(Money price) {
        this.price = price;
    }

    @Override
    public Tax getTax() {
        return tax;
    }

    @Override
    public void setTax(Tax tax) {
        this.tax = tax;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Money getAmount() {
        Money amount = null;

        Number qty = this.getQuantity() != null ? this.getQuantity().getValue() : null;
        Money price = this.getPrice() != null ? this.getPrice() : null;

        if (qty != null && price != null) {            
            amount = price.multipliedBy(qty.longValue());
        }

        return amount;
    }
}
