package io.company.brewcraft.pojo;

import javax.measure.Quantity;

import org.joda.money.Money;

public interface BaseInvoiceItem {
    String getDescription();

    void setDescription(String description);
    
    Invoice getInvoice();
    
    void setInvoice(Invoice invoice);

    Quantity<?> getQuantity();

    void setQuantity(Quantity<?> quantity);

    Money getPrice();

    void setPrice(Money price);

    Tax getTax();

    void setTax(Tax tax);

    Material getMaterial();

    void setMaterial(Material material);
}
