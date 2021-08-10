package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.EquipmentAccessor;

public interface EnhancedEquipmentRepository {

    void refreshAccessors(Collection<? extends EquipmentAccessor> accessors);
}
