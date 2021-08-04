package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.BrewStageStatus;

public interface BrewStageStatusService {
    
	List<BrewStageStatus> getStatuses();
    
    BrewStageStatus getStatus(String name);
    
}
