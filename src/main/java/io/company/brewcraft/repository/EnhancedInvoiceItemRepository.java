package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.service.InvoiceItemAccessor;

public interface EnhancedInvoiceItemRepository {
    void refresh(Collection<InvoiceItem> items);

    void refreshAccessors(Collection<? extends InvoiceItemAccessor> accessors);
}
