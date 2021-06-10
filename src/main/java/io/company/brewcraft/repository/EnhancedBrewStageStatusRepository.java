package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.BrewStageStatusAccessor;

public interface EnhancedBrewStageStatusRepository {
	
    void refreshAccessors(Collection<? extends BrewStageStatusAccessor> accessors);

}
