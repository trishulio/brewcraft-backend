package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionAccessor;

public class FinishedGoodLotFinishedGoodLotPortionRefresher implements Refresher<FinishedGoodLotFinishedGoodLotPortion, FinishedGoodLotFinishedGoodLotPortionAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotFinishedGoodLotPortionRefresher.class);

    private final Refresher<FinishedGoodLot, FinishedGoodLotAccessor> finishedGoodLotRefresher;

    private final AccessorRefresher<Long, FinishedGoodLotFinishedGoodLotPortionAccessor, FinishedGoodLotFinishedGoodLotPortion> refresher;

    public FinishedGoodLotFinishedGoodLotPortionRefresher(Refresher<FinishedGoodLot, FinishedGoodLotAccessor> finishedGoodLotRefresher, AccessorRefresher<Long, FinishedGoodLotFinishedGoodLotPortionAccessor, FinishedGoodLotFinishedGoodLotPortion> refresher) {
        this.finishedGoodLotRefresher = finishedGoodLotRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodLotFinishedGoodLotPortion> portions) {
        finishedGoodLotRefresher.refreshAccessors(portions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodLotFinishedGoodLotPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
