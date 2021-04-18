package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.InvoiceStatusAccessor;

public interface EnhancedInvoiceStatusRepository {
    void refreshAccessors(Collection<? extends InvoiceStatusAccessor> accessors);
}
