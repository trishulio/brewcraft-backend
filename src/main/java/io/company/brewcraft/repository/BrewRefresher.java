package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ProductAccessor;

public class BrewRefresher implements IBrewRefresher<Brew, BrewAccessor, ParentBrewAccessor> {
    private static final Logger log = LoggerFactory.getLogger(BrewRefresher.class);

    private final AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher;

    private final AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorRefresher;

    private final Refresher<Product, ProductAccessor> productRefresher;

    public BrewRefresher(Refresher<Product, ProductAccessor> productRefresher, AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher, AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorRefresher) {
        this.productRefresher = productRefresher;
        this.parentBrewRefresher = parentBrewRefresher;
        this.brewAccessorRefresher = brewAccessorRefresher;
    }

    @Override
    public void refresh(Collection<Brew> brews) {
        this.productRefresher.refreshAccessors(brews);
        this.refreshParentBrewAccessors(brews);
    }

    @Override
    public void refreshParentBrewAccessors(Collection<? extends ParentBrewAccessor> accessors) {
        this.parentBrewRefresher.refreshAccessors(accessors);
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewAccessor> accessors) {
        this.brewAccessorRefresher.refreshAccessors(accessors);
    }
}
