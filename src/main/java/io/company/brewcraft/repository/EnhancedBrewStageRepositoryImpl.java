package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.service.BrewStageAccessor;

public class EnhancedBrewStageRepositoryImpl implements EnhancedBrewStageRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedBrewStageRepositoryImpl.class);
    
    private AccessorRefresher<Long, BrewStageAccessor, BrewStage> refresher;

    private BrewTaskRepository brewTaskRepository;
    
    private BrewStageStatusRepository brewStageStatusRepository;
    
    private BrewRepository brewRepository;
        
    public EnhancedBrewStageRepositoryImpl(BrewTaskRepository brewTaskRepository, BrewStageStatusRepository brewStageStatusRepository, BrewRepository brewRepository, AccessorRefresher<Long, BrewStageAccessor, BrewStage> refresher) {
        this.brewStageStatusRepository = brewStageStatusRepository;
        this.brewTaskRepository = brewTaskRepository;
        this.brewRepository = brewRepository;
        this.refresher = refresher;
    }

	@Override
	public void refresh(Collection<BrewStage> brewStages) {
        this.brewStageStatusRepository.refreshAccessors(brewStages);
        this.brewTaskRepository.refreshAccessors(brewStages);
        this.brewRepository.refreshAccessors(brewStages);
	}
	
	@Override
	public void refreshAccessors(Collection<? extends BrewStageAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
	}
}
