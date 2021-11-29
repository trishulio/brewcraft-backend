package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;

public class FinishedGoodMaterialPortionRefresher implements Refresher<FinishedGoodMaterialPortion, FinishedGoodMaterialPortionAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodMaterialPortionRefresher.class);

    private final Refresher<MaterialLot, MaterialLotAccessor> materialLotRepo;

    private final AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> refresher;

    public FinishedGoodMaterialPortionRefresher(Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher, AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> refresher) {
        this.materialLotRepo = materialLotRefresher;
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
