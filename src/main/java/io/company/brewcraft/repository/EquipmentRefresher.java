package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.service.EquipmentAccessor;

public class EquipmentRefresher implements Refresher<Equipment, EquipmentAccessor> {
    private static final Logger log = LoggerFactory.getLogger(EquipmentRefresher.class);

    private final AccessorRefresher<Long, EquipmentAccessor, Equipment> refresher;

    public EquipmentRefresher(AccessorRefresher<Long, EquipmentAccessor, Equipment> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends EquipmentAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<Equipment> entities) {
        // NOTE: Not needed at this time
    }
}
