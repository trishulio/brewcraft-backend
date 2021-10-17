package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;

public interface EnhancedFinishedGoodMixturePortionRepository {
    void refresh(Collection<FinishedGoodMixturePortion> portions);

    void refreshAccessors(Collection<? extends FinishedGoodMixturePortionAccessor> accessors);
}
