package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.MaterialAccessor;

public interface EnhancedMaterialRepository {
    void refreshAccessors(Collection<? extends MaterialAccessor> accessors);
}
