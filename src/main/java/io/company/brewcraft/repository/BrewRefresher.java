package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;

public class BrewRefresher implements EnhancedBrewRepository {
    private static final Logger log = LoggerFactory.getLogger(BrewRefresher.class);

    private AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher;

    private AccessorRefresher<Long, BrewAccessor, Brew> brewRefresher;

    private ProductRepository productRepository;

    private BrewRepository brewRepository;

    public BrewRefresher(ProductRepository productRepository, BrewRepository brewRepository, AccessorRefresher<Long, ParentBrewAccessor, Brew> parentBrewRefresher, AccessorRefresher<Long, BrewAccessor, Brew> brewRefresher) {
        this.productRepository = productRepository;
        this.brewRepository = brewRepository;
        this.parentBrewRefresher = parentBrewRefresher;
        this.brewRefresher = brewRefresher;
    }

    @Override
    public void refresh(Collection<Brew> brews) {
        this.productRepository.refreshAccessors(brews);
        this.brewRepository.refreshParentBrewAccessors(brews);
    }

    @Override
    public void refreshParentBrewAccessors(Collection<? extends ParentBrewAccessor> accessors) {
        this.parentBrewRefresher.refreshAccessors(accessors);
    }

    @Override
    public void refreshAccessors(Collection<? extends BrewAccessor> accessors) {
        this.brewRefresher.refreshAccessors(accessors);
    }
}
