package io.company.brewcraft.repository;

import io.company.brewcraft.pojo.Invoice;

public interface EnhancedInvoiceRepository {
    Invoice refreshAndAdd(Long purchaseOrderId, Invoice invoice);
}
