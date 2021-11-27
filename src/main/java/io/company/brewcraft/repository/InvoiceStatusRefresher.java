package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.service.InvoiceStatusAccessor;

public class InvoiceStatusRefresher implements EnhancedInvoiceStatusRepository {
    private static final Logger log = LoggerFactory.getLogger(InvoiceStatusRefresher.class);

    private AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> refresher;

    public InvoiceStatusRefresher(AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
