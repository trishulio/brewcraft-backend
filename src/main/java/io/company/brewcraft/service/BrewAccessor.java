package io.company.brewcraft.service;

import io.company.brewcraft.model.Brew;

public interface BrewAccessor {
    final String ATTR_BREW = "brew";

    Brew getBrew();
    
    void setBrew(Brew brew);
}
