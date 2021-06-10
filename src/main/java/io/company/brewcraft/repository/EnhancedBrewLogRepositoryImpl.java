package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BrewLog;

public class EnhancedBrewLogRepositoryImpl implements EnhancedBrewLogRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedBrewLogRepositoryImpl.class);
    
    private BrewLogTypeRepository brewLogTypeRepository;
    
    private BrewStageRepository brewStageRepository;
    
    public EnhancedBrewLogRepositoryImpl(BrewLogTypeRepository brewLogTypeRepository, BrewStageRepository brewStageRepository) {
        this.brewLogTypeRepository = brewLogTypeRepository;
        this.brewStageRepository = brewStageRepository;
    }

	@Override
	public void refresh(Collection<BrewLog> brewLogs) {
        this.brewLogTypeRepository.refreshAccessors(brewLogs);
        this.brewStageRepository.refreshAccessors(brewLogs);
	}
	
}
