package io.company.brewcraft.repository;

import io.company.brewcraft.model.InvoiceEntity;

public interface EnhancedInvoiceRepository {
    InvoiceEntity refreshAndAdd(Long purchaseOrderId, InvoiceEntity invoice);
}
