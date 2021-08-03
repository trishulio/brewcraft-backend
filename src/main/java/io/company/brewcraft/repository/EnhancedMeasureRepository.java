package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.MeasureAccessor;

public interface EnhancedMeasureRepository {
	
    void refreshAccessors(Collection<? extends MeasureAccessor> accessors);

}
