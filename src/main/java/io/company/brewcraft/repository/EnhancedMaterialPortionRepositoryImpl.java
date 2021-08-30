package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.service.MaterialPortionAccessor;

public class EnhancedMaterialPortionRepositoryImpl implements EnhancedMaterialPortionRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMaterialPortionRepositoryImpl.class);

    private MixtureRepository mixtureRepository;

    private MaterialLotRepository materialLotRepository;
    
    private final AccessorRefresher<Long, MaterialPortionAccessor, MaterialPortion> refresher;

    public EnhancedMaterialPortionRepositoryImpl(MixtureRepository mixtureRepository, MaterialLotRepository materialLotRepository, AccessorRefresher<Long, MaterialPortionAccessor, MaterialPortion> refresher) {
        this.mixtureRepository = mixtureRepository;
        this.materialLotRepository = materialLotRepository;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MaterialPortion> materialPortions) {
        mixtureRepository.refreshAccessors(materialPortions);
        materialLotRepository.refreshAccessors(materialPortions);
    }

    @Override
    public void refreshAccessors(Collection<? extends MaterialPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
