package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.BrewStageStatus;

public interface BrewStageStatusService {
    
	public List<BrewStageStatus> getStatuses();
    
    public BrewStageStatus getStatus(String name);
    
}
