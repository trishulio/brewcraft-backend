package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.ParentMixtureAccessor;

public class EnhancedMixtureRepositoryImpl implements EnhancedMixtureRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureRepositoryImpl.class);
    
    private AccessorRefresher<Long, ParentMixtureAccessor, Mixture> refresher;
    
    private MixtureRepository mixtureRepository;
    
    private EquipmentRepository equipmentRepository;
    
    private BrewStageRepository brewStageRepository;
        
    private MaterialPortionRepository materialPortionRepository;
    
    public EnhancedMixtureRepositoryImpl(MixtureRepository mixtureRepository, EquipmentRepository equipmentRepository, BrewStageRepository brewStageRepository, MaterialPortionRepository materialPortionRepository, AccessorRefresher<Long, ParentMixtureAccessor, Mixture> refresher) {
        this.mixtureRepository = mixtureRepository;
        this.equipmentRepository = equipmentRepository;
        this.brewStageRepository = brewStageRepository;
        this.materialPortionRepository = materialPortionRepository;
        this.refresher = refresher;
    }

	@Override
	public void refresh(Collection<Mixture> mixtures) {
        this.mixtureRepository.refreshAccessors(mixtures);
        this.equipmentRepository.refreshAccessors(mixtures);
        this.brewStageRepository.refreshAccessors(mixtures);
        
        List<MaterialPortion> materialPortions = mixtures.stream().filter(i -> i.getMaterialPortions() != null).flatMap(i -> i.getMaterialPortions().stream()).collect(Collectors.toList());
        materialPortionRepository.refresh(materialPortions);
        
        //todo recordings
        
	}

	@Override
	public void refreshAccessors(Collection<? extends ParentMixtureAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
	}
}
