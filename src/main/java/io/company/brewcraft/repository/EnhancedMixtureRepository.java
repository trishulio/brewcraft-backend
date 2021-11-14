package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixturesAccessor;

public interface EnhancedMixtureRepository {
    void refresh(Collection<Mixture> mixtures);

    void refreshAccessors(Collection<? extends MixtureAccessor> accessors);
    
    void refreshParentMixturesAccessors(Collection<? extends ParentMixturesAccessor> accessors);
}
