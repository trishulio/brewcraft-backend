package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.MaterialLotAccessor;

public interface EnhancedMaterialLotRepository {
    void refresh(Collection<MaterialLot> lots);

    void refreshAccessors(Collection<? extends MaterialLotAccessor> accessors);
}
