package io.company.brewcraft.service;

import io.company.brewcraft.model.BrewStageStatus;

public interface BrewStageStatusAccessor {
    final String ATTR_BREW_STAGE_STATUS = "status";

    BrewStageStatus getStatus();

    void setStatus(BrewStageStatus brewStageStatus);
}
