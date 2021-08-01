package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.BrewTask;

public interface BrewTaskService {
    
    BrewTask getTask(String name);
    
    List<BrewTask> getTasks();

}
