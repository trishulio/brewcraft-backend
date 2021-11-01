package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;

public class EnhancedFinishedGoodMaterialPortionRepositoryImpl implements EnhancedFinishedGoodMaterialPortionRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedFinishedGoodMaterialPortionRepositoryImpl.class);

    private MaterialLotRepository materialLotRepo;

    private final AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> refresher;

    public EnhancedFinishedGoodMaterialPortionRepositoryImpl(MaterialLotRepository materialLotRepo, AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> refresher) {
        this.materialLotRepo = materialLotRepo;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodMaterialPortion> portions) {
        materialLotRepo.refreshAccessors(portions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodMaterialPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
