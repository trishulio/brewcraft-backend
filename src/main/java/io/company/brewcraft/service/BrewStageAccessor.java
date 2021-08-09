package io.company.brewcraft.service;

import io.company.brewcraft.model.BrewStage;

public interface BrewStageAccessor {
    final String ATTR_BREW_STAGE = "brewStage";

    BrewStage getBrewStage();

    void setBrewStage(BrewStage brewStage);
}
