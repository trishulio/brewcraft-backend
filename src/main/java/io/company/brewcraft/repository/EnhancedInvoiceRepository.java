package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Invoice;

public interface EnhancedInvoiceRepository {
    void refresh(Collection<Invoice> invoices);
}
