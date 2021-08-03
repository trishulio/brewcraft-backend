package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.service.BrewStageStatusAccessor;

public class EnhancedBrewStageStatusRepositoryImpl implements EnhancedBrewStageStatusRepository {
	
    private AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> refresher;
    
    public EnhancedBrewStageStatusRepositoryImpl(AccessorRefresher<Long, BrewStageStatusAccessor, BrewStageStatus> refresher) {
        this.refresher = refresher;
    }
	
	@Override
	public void refreshAccessors(Collection<? extends BrewStageStatusAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
	}

}
