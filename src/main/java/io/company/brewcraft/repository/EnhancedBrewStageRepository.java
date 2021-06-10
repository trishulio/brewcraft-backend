package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.service.BrewStageAccessor;

public interface EnhancedBrewStageRepository {
    void refresh(Collection<BrewStage> brewStages);
    
    void refreshAccessors(Collection<? extends BrewStageAccessor> accessors);
}
