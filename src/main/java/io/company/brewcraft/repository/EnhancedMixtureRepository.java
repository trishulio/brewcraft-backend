package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.service.MixtureAccessor;
import io.company.brewcraft.service.ParentMixtureAccessor;

public interface EnhancedMixtureRepository {
    void refresh(Collection<Mixture> mixtures);

    void refreshParentMixtureAccessors(Collection<? extends ParentMixtureAccessor> accessors);

    void refreshAccessors(Collection<? extends MixtureAccessor> accessors);
}
