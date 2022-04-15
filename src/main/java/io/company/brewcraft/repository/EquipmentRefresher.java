package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.service.EquipmentAccessor;
import io.company.brewcraft.service.EquipmentTypeAccessor;
import io.company.brewcraft.service.FacilityAccessor;

public class EquipmentRefresher implements Refresher<Equipment, EquipmentAccessor> {
    private static final Logger log = LoggerFactory.getLogger(EquipmentRefresher.class);

    private final AccessorRefresher<Long, EquipmentAccessor, Equipment> refresher;
    private final Refresher<Facility, FacilityAccessor> facilityRefresher;
    private final Refresher<EquipmentType, EquipmentTypeAccessor> typeRefresher;

    public EquipmentRefresher(AccessorRefresher<Long, EquipmentAccessor, Equipment> refresher, Refresher<Facility, FacilityAccessor> facilityRefresher, Refresher<EquipmentType, EquipmentTypeAccessor> typeRefresher) {
        this.refresher = refresher;
        this.facilityRefresher = facilityRefresher;
        this.typeRefresher = typeRefresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends EquipmentAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<Equipment> entities) {
        this.facilityRefresher.refreshAccessors(entities);
        this.typeRefresher.refreshAccessors(entities);
    }
}
