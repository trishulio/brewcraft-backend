package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Brew;
import io.company.brewcraft.service.BrewAccessor;
import io.company.brewcraft.service.ParentBrewAccessor;

public interface EnhancedBrewRepository {
    void refresh(Collection<Brew> brews);
    
    void refreshParentBrewAccessors(Collection<? extends ParentBrewAccessor> accessors);
    
    void refreshAccessors(Collection<? extends BrewAccessor> accessors);
}
