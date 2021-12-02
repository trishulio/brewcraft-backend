package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;
import io.company.brewcraft.service.MixtureAccessor;

public class FinishedGoodMixturePortionRefresher implements Refresher<FinishedGoodMixturePortion, FinishedGoodMixturePortionAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodMixturePortionRefresher.class);

    private final Refresher<Mixture, MixtureAccessor> mixtureRefresher;

    private final AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> refresher;

    public FinishedGoodMixturePortionRefresher(Refresher<Mixture, MixtureAccessor> mixtureRefresher, AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> refresher) {
        this.mixtureRefresher = mixtureRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodMixturePortion> portions) {
        mixtureRefresher.refreshAccessors(portions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodMixturePortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
