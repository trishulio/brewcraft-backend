package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.service.EquipmentTypeAccessor;

public class EquipmentTypeRefresher implements Refresher<EquipmentType, EquipmentTypeAccessor> {
    private static final Logger log = LoggerFactory.getLogger(EquipmentTypeRefresher.class);

    private final AccessorRefresher<Long, EquipmentTypeAccessor, EquipmentType> refresher;

    public EquipmentTypeRefresher(AccessorRefresher<Long, EquipmentTypeAccessor, EquipmentType> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends EquipmentTypeAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<EquipmentType> entities) {
        // Not needed at this time
    }
}
