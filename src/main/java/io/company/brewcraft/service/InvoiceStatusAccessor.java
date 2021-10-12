package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceStatus;

public interface InvoiceStatusAccessor {
    final String ATTR_INVOICE_STATUS = "invoiceStatus";

    InvoiceStatus getInvoiceStatus();

    void setInvoiceStatus(InvoiceStatus status);
}
