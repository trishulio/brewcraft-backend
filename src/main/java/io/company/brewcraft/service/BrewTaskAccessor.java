package io.company.brewcraft.service;

import io.company.brewcraft.model.BrewTask;

public interface BrewTaskAccessor {
    final String ATTR_BREW_TASK = "task";

    BrewTask getTask();
    
    void setTask(BrewTask brewTask);
}
