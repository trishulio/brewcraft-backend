package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.service.ProductAccessor;

public class ProductRefresher implements Refresher<Product, ProductAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ProductRefresher.class);

    private final AccessorRefresher<Long, ProductAccessor, Product> refresher;

    public ProductRefresher(AccessorRefresher<Long, ProductAccessor, Product> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends ProductAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<Product> entities) {
        // NOTE: Not needed at this time
    }
}
