package io.company.brewcraft.service;

import io.company.brewcraft.model.BrewLog;

public interface BrewLogAccessor {
    final String ATTR_BREW_LOG = "brewLog";

    BrewLog getBrewLog();
    
    void setBrewLog(BrewLog brewLog);
}
