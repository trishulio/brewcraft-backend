package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.ProductMeasureAccessor;

public interface EnhancedProductMeasureRepository {
	
    void refreshAccessors(Collection<? extends ProductMeasureAccessor> accessors);

}
