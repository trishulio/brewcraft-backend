package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.SupplierAccessor;

public class EnhancedSupplierRepositoryImpl implements EnhancedSupplierRepository {
    
    private AccessorRefresher<Long, SupplierAccessor, Supplier> refresher;
    
    public EnhancedSupplierRepositoryImpl(AccessorRefresher<Long, SupplierAccessor, Supplier> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<Supplier> purchaseOrders) {
        // TODO: Supplier entity needs to be refactored to work with refreshAccessor.
    }

    @Override
    public void refreshAccessors(Collection<? extends SupplierAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
