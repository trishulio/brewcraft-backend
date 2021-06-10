package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BrewLog;

public interface BrewLogService {

    public Page<BrewLog> getBrewLogs(Set<Long> ids, Set<Long> brewIds, Set<Long> brewStageIds, Set<String> types, int page, int size, Set<String> sort, boolean orderAscending);
    
    public BrewLog getBrewLog(Long brewLogId);

    public BrewLog addBrewLog(BrewLog brewLog, Long stageId, String type);
            
    public BrewLog putBrewLog(Long brewLogId, BrewLog brewLog);
        
    public BrewLog patchBrewLog(Long brewLogId, BrewLog brewLog);
    
    public void deleteBrewLog(Long brewLogId);
    
    public boolean brewLogExists(Long brewLogId);
    
 }

