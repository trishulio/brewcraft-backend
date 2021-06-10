package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.BrewLogTypeAccessor;

public interface EnhancedBrewLogTypeRepository {
	
    void refreshAccessors(Collection<? extends BrewLogTypeAccessor> accessors);
    
}
