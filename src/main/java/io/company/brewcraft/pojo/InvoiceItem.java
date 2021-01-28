package io.company.brewcraft.pojo;

import javax.measure.Quantity;

import org.joda.money.Money;

import io.company.brewcraft.model.BaseModel;
import io.company.brewcraft.service.MoneySupplier;

public class InvoiceItem extends BaseModel implements MoneySupplier {
    private Long id;
    private String lotNumber;
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

    public InvoiceItem(Long id, String lotNumber, String description, Quantity<?> quantity, Money price, Tax tax, Material material, Integer version) {
        this(id);
        setLotNumber(lotNumber);
        setDescription(description);
        setQuantity(quantity);
        setPrice(price);
        setTax(tax);
        setMaterial(material);
        setVersion(version);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Quantity<?> getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity<?> quantity) {
        this.quantity = quantity;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getVersion() {
        return version;
    }

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
