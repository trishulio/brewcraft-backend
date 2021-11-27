package io.company.brewcraft.repository;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public interface EnhancedPurchaseOrderRepository extends Refresher<PurchaseOrder, PurchaseOrderAccessor> {
}
