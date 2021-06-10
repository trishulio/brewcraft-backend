package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseBrewStage;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.UpdateBrewStage;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class BrewStageServiceImpl extends BaseService implements BrewStageService {
    private static final Logger log = LoggerFactory.getLogger(BrewStageServiceImpl.class);
        
    private BrewStageRepository brewStageRepository;
    
    private BrewService brewService;
            
    public BrewStageServiceImpl(BrewStageRepository brewStageRepository, BrewService brewService) {
        this.brewStageRepository = brewStageRepository;
        this.brewService = brewService;
    }

	@Override
	public Page<BrewStage> getBrewStages(Set<Long> ids, Set<Long> brewIds, Set<Long> statusIds, Set<Long> taskIds,
			Set<Long> brewLogIds, LocalDateTime startedAtFrom, LocalDateTime startedAtTo, LocalDateTime endedAtFrom,
			LocalDateTime endedAtTo, int page, int size, Set<String> sort, boolean orderAscending) {
			
			Specification<BrewStage> spec = SpecificationBuilder
                .builder()
                .in(BrewStage.FIELD_ID, ids)
                .in(new String[] {BrewStage.FIELD_BREW, Brew.FIELD_ID}, brewIds)
                .in(new String[] {BrewStage.FIELD_STATUS, BrewStageStatus.FIELD_ID}, statusIds)
                .in(new String[] {BrewStage.FIELD_TASK, BrewTask.FIELD_ID}, taskIds)
                .in(new String[] {BrewStage.FIELD_BREW_LOGS, BrewTask.FIELD_ID}, brewLogIds)
                .between(Brew.FIELD_STARTED_AT, startedAtFrom, startedAtTo)
                .between(Brew.FIELD_ENDED_AT, endedAtFrom, endedAtTo)
                .build();
            
            Page<BrewStage> brewPage = brewStageRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return brewPage;
	}

    @Override
    public BrewStage getBrewStage(Long brewStageId) {
        BrewStage brewStage = brewStageRepository.findById(brewStageId).orElse(null);

        return brewStage;
    }
    
    @Override
    public BrewStage addBrewStage(Long brewId, BrewStage brewStage) {
        Brew brew = Optional.ofNullable(brewService.getBrew(brewId)).orElseThrow(() -> new EntityNotFoundException("Brew", brewId.toString()));
        brewStage.setBrew(brew);
        
        brewStageRepository.refresh(List.of(brewStage));
        
        BrewStage addedBrew = brewStageRepository.saveAndFlush(brewStage);
        
        return addedBrew;
    }
    
    @Override
    public BrewStage putBrewStage(Long brewId, Long brewStageId, BrewStage putBrewStage) {
        brewStageRepository.refresh(List.of(putBrewStage));

        BrewStage existingBrewStage = getBrewStage(brewStageId);          

        if (existingBrewStage == null) {
            existingBrewStage = putBrewStage;
            existingBrewStage.setId(brewStageId);
        } else {
        	existingBrewStage.optimisticLockCheck(putBrewStage);
            existingBrewStage.override(putBrewStage, getPropertyNames(BaseBrewStage.class));
        }
        
        return addBrewStage(brewId, existingBrewStage);
    }
    
    
    @Override
    public BrewStage patchBrewStage(Long brewStageId, BrewStage brewStagePatch) {           
        BrewStage existingBrewStage = Optional.ofNullable(getBrewStage(brewStageId)).orElseThrow(() -> new EntityNotFoundException("BrewStage", brewStageId.toString()));            
        
        brewStageRepository.refresh(List.of(brewStagePatch));
        
    	existingBrewStage.optimisticLockCheck(brewStagePatch);
        
        existingBrewStage.outerJoin(brewStagePatch, getPropertyNames(UpdateBrewStage.class));
        
        return addBrewStage(existingBrewStage.getBrew().getId(), existingBrewStage);
    }
    
    @Override
    public void deleteBrewStage(Long brewStageId) {
        brewStageRepository.deleteById(brewStageId);                        
    }
    
    @Override
    public boolean brewStageExists(Long brewStageId) {
        return brewStageRepository.existsById(brewStageId);
    }
}
