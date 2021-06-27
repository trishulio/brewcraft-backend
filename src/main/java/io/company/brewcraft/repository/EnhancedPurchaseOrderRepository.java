package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public interface EnhancedPurchaseOrderRepository {
    void refresh(Collection<PurchaseOrder> purchaseOrders);
    void refreshAccessors(Collection<? extends PurchaseOrderAccessor> accessors);
}
