package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;

public interface EnhancedFinishedGoodMaterialPortionRepository {
    void refresh(Collection<FinishedGoodMaterialPortion> portions);

    void refreshAccessors(Collection<? extends FinishedGoodMaterialPortionAccessor> accessors);
}
