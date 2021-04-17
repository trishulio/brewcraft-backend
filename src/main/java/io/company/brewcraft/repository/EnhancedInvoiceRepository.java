package io.company.brewcraft.repository;

import io.company.brewcraft.model.Invoice;

public interface EnhancedInvoiceRepository {
    void refresh(Long purchaseOrderId, Invoice invoice);
}
