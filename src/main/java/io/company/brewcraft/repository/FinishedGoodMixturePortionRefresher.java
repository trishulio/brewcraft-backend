package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;

public class FinishedGoodMixturePortionRefresher implements EnhancedFinishedGoodMixturePortionRepository{
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodMixturePortionRefresher.class);

    private MixtureRepository mixtureRepo;

    private final AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> refresher;

    public FinishedGoodMixturePortionRefresher(MixtureRepository mixtureRepo, AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> refresher) {
        this.mixtureRepo = mixtureRepo;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodMixturePortion> portions) {
        mixtureRepo.refreshAccessors(portions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodMixturePortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
