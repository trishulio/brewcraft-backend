package io.company.brewcraft.model;

import javax.measure.Quantity;

import org.joda.money.Money;

import io.company.brewcraft.service.MaterialAccessor;

public interface BaseInvoiceItem extends MaterialAccessor {
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
}
