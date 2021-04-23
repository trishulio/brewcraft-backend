package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.service.InvoiceStatusAccessor;

public class EnhancedInvoiceStatusRepositoryImpl implements EnhancedInvoiceStatusRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceStatusRepositoryImpl.class);
    
    private AccessorRefresher<InvoiceStatusAccessor, InvoiceStatus> refresher;
    
    public EnhancedInvoiceStatusRepositoryImpl(AccessorRefresher<InvoiceStatusAccessor, InvoiceStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
