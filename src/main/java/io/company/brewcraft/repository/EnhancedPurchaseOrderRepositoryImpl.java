package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public class EnhancedPurchaseOrderRepositoryImpl implements EnhancedPurchaseOrderRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedPurchaseOrderRepositoryImpl.class);
    
    private AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> refresher;
    
    private SupplierRepository supplierRepo;
    
    @Autowired
    public EnhancedPurchaseOrderRepositoryImpl(AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> refresher, SupplierRepository supplierRepo) {
        this.refresher = refresher;
        this.supplierRepo = supplierRepo;
    }

    @Override
    public void refreshAccessors(Collection<? extends PurchaseOrderAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<PurchaseOrder> purchaseOrders) {
        this.supplierRepo.refreshAccessors(purchaseOrders);
    }
}
