package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionAccessor;
import io.company.brewcraft.service.MixtureAccessor;

public class FinishedGoodLotMixturePortionRefresher implements Refresher<FinishedGoodLotMixturePortion, FinishedGoodLotMixturePortionAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotMixturePortionRefresher.class);

    private final Refresher<Mixture, MixtureAccessor> mixtureRefresher;

    private final AccessorRefresher<Long, FinishedGoodLotMixturePortionAccessor, FinishedGoodLotMixturePortion> refresher;

    public FinishedGoodLotMixturePortionRefresher(Refresher<Mixture, MixtureAccessor> mixtureRefresher, AccessorRefresher<Long, FinishedGoodLotMixturePortionAccessor, FinishedGoodLotMixturePortion> refresher) {
        this.mixtureRefresher = mixtureRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodLotMixturePortion> portions) {
        mixtureRefresher.refreshAccessors(portions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodLotMixturePortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
