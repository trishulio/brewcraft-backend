package io.company.brewcraft.model;

import org.joda.money.Money;

import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.MoneySupplier;
import io.company.brewcraft.service.TaxAccessor;

public interface BaseInvoiceItem<T extends BaseInvoice<? extends BaseInvoiceItem<T>>> extends MaterialAccessor, QuantityAccessor, MoneySupplier, TaxAccessor {
    final String ATTR_INDEX = "index";
    final String ATTR_DESCRIPTION = "description";
    final String ATTR_INVOICE = "invoice";
    final String ATTR_QUANTITY = "quantity";
    final String ATTR_PRICE = "price";
    final String ATTR_TAX = "tax";

    Integer getIndex();

    void setIndex(Integer index);

    String getDescription();

    void setDescription(String description);

    T getInvoice();

    void setInvoice(T invoice);

    Money getPrice();

    void setPrice(Money price);
}
