package io.company.brewcraft.service;

import io.company.brewcraft.model.Brew;

public interface ParentBrewAccessor {
    final String ATTR_PARENT_BREW = "parentBrew";

    Brew getParentBrew();
    
    void setParentBrew(Brew brew);
}
