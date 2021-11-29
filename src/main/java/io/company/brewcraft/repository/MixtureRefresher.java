package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;

public class MixtureRefresher implements IMixtureRefresher<Mixture, MixtureAccessor, ParentMixturesAccessor> {
    private static final Logger log = LoggerFactory.getLogger(MixtureRefresher.class);

    private final AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorRefresher;

    private final CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixtureRefresher;

    private final Refresher<Equipment, EquipmentAccessor> equipmentRefresher;

    private final Refresher<BrewStage, BrewStageAccessor> brewStageRefresher;

    public MixtureRefresher(Refresher<Equipment, EquipmentAccessor> equipmentRefresher, Refresher<BrewStage, BrewStageAccessor> brewStageRefresher, AccessorRefresher<Long, MixtureAccessor, Mixture> mixtureAccessorRefresher, CollectionAccessorRefresher<Long, ParentMixturesAccessor, Mixture> parentMixtureRefresher) {
        this.equipmentRefresher = equipmentRefresher;
        this.brewStageRefresher = brewStageRefresher;
        this.mixtureAccessorRefresher = mixtureAccessorRefresher;
        this.parentMixtureRefresher = parentMixtureRefresher;
    }

    @Override
    public void refresh(Collection<Mixture> mixtures) {
        this.refreshParentMixturesAccessors(mixtures);
        this.equipmentRefresher.refreshAccessors(mixtures);
        this.brewStageRefresher.refreshAccessors(mixtures);
    }

    @Override
    public void refreshParentMixturesAccessors(Collection<? extends ParentMixturesAccessor> accessors) {
        this.parentMixtureRefresher.refreshAccessors(accessors);
    }

    @Override
    public void refreshAccessors(Collection<? extends MixtureAccessor> accessors) {
        this.mixtureAccessorRefresher.refreshAccessors(accessors);
    }
}
