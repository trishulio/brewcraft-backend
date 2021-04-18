package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceItem;

public interface InvoiceItemAccessor {
    InvoiceItem getInvoiceItem();

    void setInvoiceItem(InvoiceItem item);
}
