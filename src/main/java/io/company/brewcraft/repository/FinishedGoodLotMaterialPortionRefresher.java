package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionAccessor;
import io.company.brewcraft.service.MaterialLotAccessor;

public class FinishedGoodLotMaterialPortionRefresher implements Refresher<FinishedGoodLotMaterialPortion, FinishedGoodLotMaterialPortionAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotMaterialPortionRefresher.class);

    private final Refresher<MaterialLot, MaterialLotAccessor> materialLotRepo;

    private final AccessorRefresher<Long, FinishedGoodLotMaterialPortionAccessor, FinishedGoodLotMaterialPortion> refresher;

    public FinishedGoodLotMaterialPortionRefresher(Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher, AccessorRefresher<Long, FinishedGoodLotMaterialPortionAccessor, FinishedGoodLotMaterialPortion> refresher) {
        this.materialLotRepo = materialLotRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodLotMaterialPortion> portions) {
        materialLotRepo.refreshAccessors(portions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodLotMaterialPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
