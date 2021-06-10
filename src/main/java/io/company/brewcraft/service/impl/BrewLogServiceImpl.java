package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.BaseBrewLog;
import io.company.brewcraft.dto.UpdateBrewLog;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewLog;
import io.company.brewcraft.model.BrewLogType;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.repository.BrewLogRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.BrewLogService;
import io.company.brewcraft.service.BrewLogTypeService;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class BrewLogServiceImpl extends BaseService implements BrewLogService {
        
    private BrewLogRepository brewLogRepository;
    
    private BrewStageService brewStageService;
    
    private BrewLogTypeService brewLogTypeService;

    public BrewLogServiceImpl(BrewLogRepository brewLogRepository, BrewStageService brewStageService, BrewLogTypeService brewLogTypeService) {
        this.brewLogRepository = brewLogRepository;
        this.brewStageService = brewStageService;
        this.brewLogTypeService = brewLogTypeService;
    }

    @Override
    public Page<BrewLog> getBrewLogs(Set<Long> ids, Set<Long> brewIds, Set<Long> brewStageIds, Set<String> types, int page, int size, Set<String> sort, boolean orderAscending) {                    
        Specification<BrewLog> spec = SpecificationBuilder
            .builder()
            .in(BrewLog.FIELD_ID, ids)
            .in(new String[] { BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_ID }, brewStageIds)
            .in(new String[] { BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_BREW, Brew.FIELD_ID }, brewIds)
            .build();
        
        Page<BrewLog> brewPage = brewLogRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return brewPage;
    }

    @Override
    public BrewLog getBrewLog(Long brewLogId) {
        BrewLog brewLog = brewLogRepository.findById(brewLogId).orElse(null);

        return brewLog;
    }

    @Override
    public BrewLog addBrewLog(BrewLog brewLog, Long stageId, String type) {
        BrewStage stage = Optional.ofNullable(brewStageService.getBrewStage(stageId)).orElseThrow(() -> new EntityNotFoundException("BrewStage", stageId.toString()));
        brewLog.setBrewStage(stage);
        
        BrewLogType brewLogType = Optional.ofNullable(brewLogTypeService.getType(type)).orElseThrow(() -> new EntityNotFoundException("BrewLogType", stageId.toString()));
        brewLog.setType(brewLogType);
        
        BrewLog addedBrewLog = brewLogRepository.saveAndFlush(brewLog);
        
        return addedBrewLog;
    }
    
    @Override
    public BrewLog putBrewLog(Long brewLogId, BrewLog putBrewLog) {                        
        BrewLog existingBrewLog = getBrewLog(brewLogId);          
        
        if (existingBrewLog == null) {
            existingBrewLog = putBrewLog;
            existingBrewLog.setId(brewLogId);
        } else {
        	existingBrewLog.optimisticLockCheck(putBrewLog);
            existingBrewLog.override(putBrewLog, getPropertyNames(BaseBrewLog.class));
        }
        
        return brewLogRepository.saveAndFlush(existingBrewLog); 
    }
    
    @Override
    public BrewLog patchBrewLog(Long brewLogId, BrewLog brewLogPatch) {           
        BrewLog existingBrewLog = Optional.ofNullable(getBrewLog(brewLogId)).orElseThrow(() -> new EntityNotFoundException("BrewLog", brewLogId.toString()));            
  
        existingBrewLog.optimisticLockCheck(brewLogPatch);
        
        existingBrewLog.outerJoin(brewLogPatch, getPropertyNames(UpdateBrewLog.class));
        
        return brewLogRepository.saveAndFlush(existingBrewLog); 
    }

    @Override
    public void deleteBrewLog(Long brewLogId) {
        brewLogRepository.deleteById(brewLogId);                        
    }
    
    @Override
    public boolean brewLogExists(Long brewLogId) {
        return brewLogRepository.existsById(brewLogId);
    }
}
