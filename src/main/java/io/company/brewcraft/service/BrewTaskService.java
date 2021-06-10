package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BrewTask;

public interface BrewTaskService {
    
    public BrewTask getTask(String name);
    
    public List<BrewTask> getTasks(Set<String> names);

}
