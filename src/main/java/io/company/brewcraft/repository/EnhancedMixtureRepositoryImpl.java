package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;

public class EnhancedMixtureRepositoryImpl implements EnhancedMixtureRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureRepositoryImpl.class);

    private AccessorRefresher<Long, ParentMixtureAccessor, Mixture> parentMixtureRefresher;

    private AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureRefresher;

    private MixtureRepository mixtureRepository;

    private EquipmentRepository equipmentRepository;

    private BrewStageRepository brewStageRepository;

    public EnhancedMixtureRepositoryImpl(MixtureRepository mixtureRepository, EquipmentRepository equipmentRepository, BrewStageRepository brewStageRepository, AccessorRefresher<Long, ParentMixtureAccessor, Mixture> parentMixtureRefresher, AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureRefresher) {
        this.mixtureRepository = mixtureRepository;
        this.equipmentRepository = equipmentRepository;
        this.brewStageRepository = brewStageRepository;
        this.parentMixtureRefresher = parentMixtureRefresher;
        this.mixtureRefresher = mixtureRefresher;
    }

    @Override
    public void refresh(Collection<Mixture> mixtures) {
        this.mixtureRepository.refreshParentMixtureAccessors(mixtures);
        this.equipmentRepository.refreshAccessors(mixtures);
        this.brewStageRepository.refreshAccessors(mixtures);
    }

    @Override
    public void refreshParentMixtureAccessors(Collection<? extends ParentMixtureAccessor> accessors) {
        this.parentMixtureRefresher.refreshAccessors(accessors);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureAccessor> accessors) {
        this.mixtureRefresher.refreshAccessors(accessors);
    }
}
