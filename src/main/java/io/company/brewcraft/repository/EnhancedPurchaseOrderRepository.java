package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.PurchaseOrderAccessor;

public interface EnhancedPurchaseOrderRepository {

    void refreshAccessors(Collection<? extends PurchaseOrderAccessor> accessors);
}
