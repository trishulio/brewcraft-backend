package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;

public interface EnhancedMixtureMaterialPortionRepository {

    void refresh(Collection<MixtureMaterialPortion> materialPortions);

    void refreshAccessors(Collection<? extends MixtureMaterialPortionAccessor> accessors);

}
