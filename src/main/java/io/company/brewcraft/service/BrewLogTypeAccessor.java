package io.company.brewcraft.service;

import io.company.brewcraft.model.BrewLogType;

public interface BrewLogTypeAccessor {
    final String ATTR_BREW_LOG_TYPE = "type";

    BrewLogType getType();
    
    void setType(BrewLogType type);
}
