package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.SupplierAccessor;

public class PurchaseOrderRefresher implements Refresher<PurchaseOrder, PurchaseOrderAccessor> {
    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderRefresher.class);

    private final AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> refresher;

    private final Refresher<Supplier, SupplierAccessor> supplierRefresher;

    @Autowired
    public PurchaseOrderRefresher(AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> refresher, Refresher<Supplier, SupplierAccessor> supplierRefresher) {
        this.refresher = refresher;
        this.supplierRefresher = supplierRefresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends PurchaseOrderAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<PurchaseOrder> purchaseOrders) {
        this.supplierRefresher.refreshAccessors(purchaseOrders);
    }
}
