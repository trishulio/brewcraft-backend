package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;

public class EnhancedMixtureMaterialPortionRepositoryImpl implements EnhancedMixtureMaterialPortionRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMixtureMaterialPortionRepositoryImpl.class);

    private MixtureRepository mixtureRepository;

    private MaterialLotRepository materialLotRepository;
    
    private final AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> refresher;

    public EnhancedMixtureMaterialPortionRepositoryImpl(MixtureRepository mixtureRepository, MaterialLotRepository materialLotRepository, AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> refresher) {
        this.mixtureRepository = mixtureRepository;
        this.materialLotRepository = materialLotRepository;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MixtureMaterialPortion> materialPortions) {
        mixtureRepository.refreshAccessors(materialPortions);
        materialLotRepository.refreshAccessors(materialPortions);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureMaterialPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
