package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.BrewLog;

public interface EnhancedBrewLogRepository {
	
    void refresh(Collection<BrewLog> brewLogs);
    
}
