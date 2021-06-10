package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.ParentMixtureAccessor;

public class EnhancedMixtureRepositoryImpl implements EnhancedMixtureRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureRepositoryImpl.class);
    
    private AccessorRefresher<Long, ParentMixtureAccessor, Mixture> refresher;
    
    private MixtureRepository mixtureRepository;
    
    private EquipmentRepository equipmentRepository;
    
    private BrewLogRepository brewLogRepository;
        
    private MaterialPortionRepository materialPortionRepository;
    
    public EnhancedMixtureRepositoryImpl(MixtureRepository mixtureRepository, EquipmentRepository equipmentRepository, BrewLogRepository brewLogRepository, MaterialPortionRepository materialPortionRepository, AccessorRefresher<Long, ParentMixtureAccessor, Mixture> refresher) {
        this.mixtureRepository = mixtureRepository;
        this.equipmentRepository = equipmentRepository;
        this.brewLogRepository = brewLogRepository;
        this.materialPortionRepository = materialPortionRepository;
        this.refresher = refresher;
    }

	@Override
	public void refresh(Collection<Mixture> mixtures) {
        this.mixtureRepository.refreshAccessors(mixtures);
        this.equipmentRepository.refreshAccessors(mixtures);
        //this.brewLogRepository.refreshAccessors(mixtures);
        //this.materialPortionRepository.refreshAccessors(mixtures);
	}

	@Override
	public void refreshAccessors(Collection<? extends ParentMixtureAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
	}
}
