package io.company.brewcraft.service;

import io.company.brewcraft.model.Mixture;

public interface ParentMixtureAccessor {
    final String ATTR_PARENT_MIXTURE = "parentMixture";

    Mixture getParentMixture();
    
    void setParentMixture(Mixture parentMixture);
}
