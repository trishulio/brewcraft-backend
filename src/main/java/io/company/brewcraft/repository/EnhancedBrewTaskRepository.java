package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.BrewTaskAccessor;

public interface EnhancedBrewTaskRepository {

    void refreshAccessors(Collection<? extends BrewTaskAccessor> accessors);

}
