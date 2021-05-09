package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceItem;

public interface InvoiceItemAccessor {
    final String ATTR_INVOICE_ITEM = "invoiceItem";

    InvoiceItem getInvoiceItem();

    void setInvoiceItem(InvoiceItem item);
}
