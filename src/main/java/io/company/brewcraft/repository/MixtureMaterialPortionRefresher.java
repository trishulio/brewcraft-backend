package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;

public class MixtureMaterialPortionRefresher implements Refresher<MixtureMaterialPortion, MixtureMaterialPortionAccessor> {
    private static final Logger log = LoggerFactory.getLogger(MixtureMaterialPortionRefresher.class);

    private final Refresher<Mixture, MixtureAccessor> mixtureRefresher;

    private final Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher;

    private final AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> refresher;

    public MixtureMaterialPortionRefresher(Refresher<Mixture, MixtureAccessor> mixtureRefresher, Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher, AccessorRefresher<Long, MixtureMaterialPortionAccessor, MixtureMaterialPortion> refresher) {
        this.mixtureRefresher = mixtureRefresher;
        this.materialLotRefresher = materialLotRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refresh(Collection<MixtureMaterialPortion> materialPortions) {
        mixtureRefresher.refreshAccessors(materialPortions);
        materialLotRefresher.refreshAccessors(materialPortions);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureMaterialPortionAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
