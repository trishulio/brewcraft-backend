package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.service.MaterialPortionAccessor;

public interface EnhancedMaterialPortionRepository {

    void refresh(Collection<MaterialPortion> materialPortions);

    void refreshAccessors(Collection<? extends MaterialPortionAccessor> accessors);

}
