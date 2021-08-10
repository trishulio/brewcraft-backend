package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceStatus;

public interface InvoiceStatusAccessor {
    final String ATTR_STATUS = "status";

    InvoiceStatus getStatus();

    void setStatus(InvoiceStatus status);
}
