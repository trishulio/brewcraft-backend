package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BrewStageStatus;

public interface BrewStageStatusService {
    
    public BrewStageStatus getStatus(String name);
    
    public List<BrewStageStatus> getStatuses(Set<String> names);

}
