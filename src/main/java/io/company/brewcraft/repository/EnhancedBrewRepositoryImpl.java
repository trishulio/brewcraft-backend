package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.service.ParentBrewAccessor;

public class EnhancedBrewRepositoryImpl implements EnhancedBrewRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedBrewRepositoryImpl.class);
    
    private AccessorRefresher<Long, ParentBrewAccessor, Brew> refresher;

    private ProductRepository productRepository;
    
    private BrewRepository brewRepository;
    
    public EnhancedBrewRepositoryImpl(ProductRepository productRepository, BrewRepository brewRepository, BrewStageRepository brewStageRepository, AccessorRefresher<Long, ParentBrewAccessor, Brew> refresher) {
        this.productRepository = productRepository;
        this.brewRepository = brewRepository;
        this.refresher = refresher;
    }

	@Override
	public void refresh(Collection<Brew> brews) {
        this.productRepository.refreshAccessors(brews);
        this.brewRepository.refreshAccessors(brews);
	}

	@Override
	public void refreshAccessors(Collection<? extends ParentBrewAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
	}
}
