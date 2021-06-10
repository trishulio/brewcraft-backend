package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.ProductAccessor;

public interface EnhancedProductRepository {

    void refreshAccessors(Collection<? extends ProductAccessor> accessors);
}
