package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.SupplierAccessor;

public interface EnhancedSupplierRepository {
    void refresh(Collection<Supplier> purchaseOrders);
    void refreshAccessors(Collection<? extends SupplierAccessor> accessors);
}
