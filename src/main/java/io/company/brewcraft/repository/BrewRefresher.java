package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.user.User;
import io.company.brewcraft.model.user.UserAccessor;
import io.company.brewcraft.repository.user.impl.UserRefresher;
import io.company.brewcraft.service.AssignedToAccessor;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.OwnedByAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;
import io.company.brewcraft.service.ProductAccessor;

public class BrewRefresher implements Refresher<Brew, BrewAccessor>, SelfParentRefresher<ParentBrewAccessor> {
    private static final Logger log = LoggerFactory.getLogger(BrewRefresher.class);

    private final AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher;

    private final AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorRefresher;

    private final Refresher<Product, ProductAccessor> productRefresher;

    private final UserRefresher userRefresher;

    public BrewRefresher(Refresher<Product, ProductAccessor> productRefresher, UserRefresher userRefresher, AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher, AccessorRefresher<Long, BrewAccessor, Brew> brewAccessorRefresher) {
        this.productRefresher = productRefresher;
        this.userRefresher = userRefresher;
        this.parentBrewRefresher = parentBrewRefresher;
        this.brewAccessorRefresher = brewAccessorRefresher;
    }

    @Override
    public void refresh(Collection<Brew> brews) {
        this.productRefresher.refreshAccessors(brews);
        this.userRefresher.refreshAssignedToAccessors(brews);
        this.userRefresher.refreshOwnedByAccessors(brews);
        this.refreshParentAccessors(brews);
    }

    @Override
    public void refreshParentAccessors(Collection<? extends ParentBrewAccessor> accessors) {
        this.parentBrewRefresher.refreshAccessors(accessors);
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewAccessor> accessors) {
        this.brewAccessorRefresher.refreshAccessors(accessors);
    }
}
