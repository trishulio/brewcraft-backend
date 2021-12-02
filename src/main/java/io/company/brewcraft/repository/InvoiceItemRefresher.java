package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.MaterialAccessor;

public class InvoiceItemRefresher implements Refresher<InvoiceItem, InvoiceItemAccessor> {
    private static final Logger log = LoggerFactory.getLogger(InvoiceItemRefresher.class);

    private final Refresher<Material, MaterialAccessor> materialRefresher;

    private final AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> refresher;

    public InvoiceItemRefresher(Refresher<Material, MaterialAccessor> materialRefresher, AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> refresher) {
        this.materialRefresher = materialRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<InvoiceItem> lots) {
        materialRefresher.refreshAccessors(lots);
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceItemAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
