package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.service.ProductAccessor;

public class EnhancedProductRepositoryImpl implements EnhancedProductRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedProductRepositoryImpl.class);
    
    private AccessorRefresher<Long, ProductAccessor, Product> refresher;
    
    @Autowired
    public EnhancedProductRepositoryImpl(AccessorRefresher<Long, ProductAccessor, Product> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends ProductAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
