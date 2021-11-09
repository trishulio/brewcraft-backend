package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.service.InvoiceItemAccessor;

public class EnhancedInvoiceItemRepositoryImpl implements EnhancedInvoiceItemRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceItemRepositoryImpl.class);

    private MaterialRepository materialRepo;

    private final AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> refresher;

    public EnhancedInvoiceItemRepositoryImpl(MaterialRepository materialRepo, AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> refresher) {
        this.materialRepo = materialRepo;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<InvoiceItem> lots) {
        materialRepo.refreshAccessors(lots);
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceItemAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
