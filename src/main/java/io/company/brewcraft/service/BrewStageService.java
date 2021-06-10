package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BrewStage;

public interface BrewStageService {

    public Page<BrewStage> getBrewStages(Set<Long> ids, Set<Long> brewIds, Set<Long> statusIds, Set<Long> taskIds, Set<Long> brewLogIds, LocalDateTime startedAtFrom, LocalDateTime startedAtTo, LocalDateTime endedAtFrom, LocalDateTime endedAtTo, int page, int size, SortedSet<String> sort, boolean orderAscending);
    
    public BrewStage getBrewStage(Long brewStageId);
    
    public BrewStage addBrewStage(Long brewId, BrewStage brewStage);
    
    public BrewStage putBrewStage(Long brewId, Long brewStageId, BrewStage brewStage);
                
    public BrewStage patchBrewStage(Long brewStageId, BrewStage brewStage);
            
    public void deleteBrewStage(Long brewStageId);
    
    public boolean brewStageExists(Long brewStageId);

 }

