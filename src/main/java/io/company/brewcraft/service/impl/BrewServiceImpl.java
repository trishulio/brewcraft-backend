package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseBrew;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.UpdateBrew;
import io.company.brewcraft.repository.BrewRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class BrewServiceImpl extends BaseService implements BrewService {
    private static final Logger log = LoggerFactory.getLogger(BrewServiceImpl.class);
        
    private BrewRepository brewRepository;
        
    public BrewServiceImpl(BrewRepository brewRepository) {
        this.brewRepository = brewRepository;  
    }
    
	@Override
	public Page<Brew> getBrews(Set<Long> ids, Set<Long> batchIds, Set<String> names, Set<Long> productIds,
			Set<Long> stageTaskIds, LocalDateTime startedAtFrom, LocalDateTime startedAtTo, LocalDateTime endedAtFrom, 
			LocalDateTime endedAtTo,  int page, int size, SortedSet<String> sort, boolean orderAscending) {
			
			Specification<Brew> spec = SpecificationBuilder
                .builder()
                .in(Brew.FIELD_ID, ids)
                .in(Brew.FIELD_BATCH_ID, batchIds)
                .in(Brew.FIELD_NAME, names)
                .in(new String[] {Brew.FIELD_PRODUCT, Product.FIELD_ID}, stageTaskIds)
                .in(new String[] {Brew.FIELD_BREW_STAGES, BrewStage.FIELD_TASK, BrewTask.FIELD_ID}, stageTaskIds)
                .between(Brew.FIELD_STARTED_AT, startedAtFrom, startedAtTo)
                .between(Brew.FIELD_ENDED_AT, endedAtFrom, endedAtTo)
                .build();
            
            Page<Brew> brewPage = brewRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return brewPage;
	}

    @Override
    public Brew getBrew(Long brewId) {
        Brew brew = brewRepository.findById(brewId).orElse(null);

        return brew;
    }
    
    @Override
    public Brew addBrew(Brew brew) {  
        brewRepository.refresh(List.of(brew));

        Brew addedBrew = brewRepository.saveAndFlush(brew);
        
        return addedBrew;
    }
    
    @Override
    public Brew putBrew(Long brewId, Brew putBrew) { 
        brewRepository.refresh(List.of(putBrew));

        Brew existingBrew = getBrew(brewId);   
        
        if (existingBrew == null) {
            existingBrew = putBrew;
            existingBrew.setId(brewId);
        } else {
            existingBrew.optimisticLockCheck(putBrew);
            existingBrew.override(putBrew, getPropertyNames(BaseBrew.class));
        }
        
        return brewRepository.saveAndFlush(existingBrew); 
    }
    
    @Override
    public Brew patchBrew(Long brewId, Brew brewPatch) { 
        Brew existingBrew = Optional.ofNullable(getBrew(brewId)).orElseThrow(() -> new EntityNotFoundException("Brew", brewId.toString()));            
  
        brewRepository.refresh(List.of(brewPatch));

        existingBrew.optimisticLockCheck(brewPatch);
        
        existingBrew.outerJoin(brewPatch, getPropertyNames(UpdateBrew.class));
        
        return brewRepository.saveAndFlush(existingBrew); 
    }

    @Override
    public void deleteBrew(Long brewId) {
        brewRepository.deleteById(brewId);                        
    }
    
    @Override
    public boolean brewExists(Long brewId) {
        return brewRepository.existsById(brewId);
    }

}
