package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;

public class EnhancedMixtureRepositoryImpl implements EnhancedMixtureRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureRepositoryImpl.class);

    private AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureRefresher;

    private CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixtureRefresher;

    private MixtureRepository mixtureRepository;

    private EquipmentRepository equipmentRepository;

    private BrewStageRepository brewStageRepository;

    public EnhancedMixtureRepositoryImpl(@Lazy MixtureRepository mixtureRepository, EquipmentRepository equipmentRepository, BrewStageRepository brewStageRepository, AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureRefresher, CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixtureRefresher) {
        this.mixtureRepository = mixtureRepository;
        this.equipmentRepository = equipmentRepository;
        this.brewStageRepository = brewStageRepository;
        this.mixtureRefresher = mixtureRefresher;
        this.parentMixtureRefresher = parentMixtureRefresher;
    }

    @Override
    public void refresh(Collection<Mixture> mixtures) {
        this.mixtureRepository.refreshParentMixturesAccessors(mixtures);
        this.equipmentRepository.refreshAccessors(mixtures);
        this.brewStageRepository.refreshAccessors(mixtures);
    }

    @Override
    public void refreshParentMixturesAccessors(Collection<? extends ParentMixturesAccessor> accessors) {
        this.parentMixtureRefresher.refreshAccessors(accessors);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureAccessor> accessors) {
        this.mixtureRefresher.refreshAccessors(accessors);
    }
}
