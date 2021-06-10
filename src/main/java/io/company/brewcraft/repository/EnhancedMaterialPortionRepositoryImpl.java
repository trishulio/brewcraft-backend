package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.service.MaterialPortionAccessor;

public class EnhancedMaterialPortionRepositoryImpl implements EnhancedMaterialPortionRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMaterialPortionRepositoryImpl.class);

    private final AccessorRefresher<Long, MaterialPortionAccessor, MaterialPortion> refresher;

    public EnhancedMaterialPortionRepositoryImpl(AccessorRefresher<Long, MaterialPortionAccessor, MaterialPortion> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MaterialPortion> materialPortions) {
        //materialLotRepo.refreshAccessors(materialPortions);
        //mixtureRepo.refreshAccessors(materialPortions);
    }

    @Override
    public void refreshAccessors(Collection<? extends MaterialPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
