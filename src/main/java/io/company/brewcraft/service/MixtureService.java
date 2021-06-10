package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BrewLog;
import io.company.brewcraft.model.Mixture;

public interface MixtureService {

    public Page<Mixture> getMixtures(Set<Long> ids, Set<Long> parentMixtureIds, Set<Long> equipmentIds, Set<Long> brewIds, Set<Long> brewBatchIds, Set<Long> stageStatusIds, Set<Long> stageTaskIds, Set<Long> productIds, int page, int size, Set<String> sort, boolean orderAscending);
    
    public Mixture getMixture(Long mixtureId);
    
    public Mixture addMixture(Mixture mixture, BrewLog brewLog);

    //public Mixture addMixture(Mixture mixture, Long parentMixtureId, Long equipmentId, BrewLog brewLog, Long userId, Long brewStageId);
    
    public Mixture putMixture(Long mixtureId, Mixture mixture, BrewLog brewLog);
            
    //public Mixture putMixture(Long mixtureId, Mixture mixture, Long parentMixtureId, Long equipmentId, BrewLog brewLog, Long userId, Long brewStageId);
    
    public Mixture patchMixture(Long mixtureId, Mixture mixture, BrewLog brewLog);
        
    //public Mixture patchMixture(Long mixtureId, Mixture mixture, Long parentMixtureId, Long equipmentId, BrewLog brewLog, Long userId, Long brewStageId);
        
    public void deleteMixture(Long mixtureId);
    
    public boolean mixtureExists(Long mixtureId);

 }

