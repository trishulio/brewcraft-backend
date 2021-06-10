package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BrewLogType;
import io.company.brewcraft.service.BrewLogTypeAccessor;

public class EnhancedBrewLogTypeRepositoryImpl implements EnhancedBrewLogTypeRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedBrewLogTypeRepositoryImpl.class);
    
    private AccessorRefresher<Long, BrewLogTypeAccessor, BrewLogType> refresher;
        
    public EnhancedBrewLogTypeRepositoryImpl(AccessorRefresher<Long, BrewLogTypeAccessor, BrewLogType> refresher) {
        this.refresher = refresher;
    }
	
	@Override
	public void refreshAccessors(Collection<? extends BrewLogTypeAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
	}
}
