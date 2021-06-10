package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.MaterialPortion;

public interface EnhancedMaterialPortionRepository {
	
    void refresh(Collection<MaterialPortion> materialPortions);
	
    void refreshAccessors(Collection<? extends MaterialPortion> accessors);

}
