package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceStatus;

public interface InvoiceStatusAccessor {
    InvoiceStatus getStatus();
    
    void setStatus(InvoiceStatus status);
}
