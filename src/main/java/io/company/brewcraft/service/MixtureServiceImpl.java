package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMixture;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.UpdateMixture;
import io.company.brewcraft.repository.MixtureRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class MixtureServiceImpl extends BaseService implements MixtureService {
    private static final Logger log = LoggerFactory.getLogger(MixtureServiceImpl.class);
    
    private MixtureRepository mixtureRepository;
        
    public MixtureServiceImpl(MixtureRepository mixtureRepository) {
        this.mixtureRepository = mixtureRepository;  
    }

	@Override
	public Page<Mixture> getMixtures(Set<Long> ids, Set<Long> parentMixtureIds, Set<Long> equipmentIds,
			Set<Long> brewIds, Set<Long> brewBatchIds, Set<Long> stageStatusIds, Set<Long> stageTaskIds,
			Set<Long> productIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
		Specification<Mixture> spec = SpecificationBuilder
                .builder()
                .in(Mixture.FIELD_ID, ids)
                .in(new String[] {Mixture.FIELD_PARENT_MIXTURE, Mixture.FIELD_ID}, parentMixtureIds)
                .in(new String[] {Mixture.FIELD_EQUIPMENT, Equipment.FIELD_ID}, equipmentIds)
                //.in(new String[] {Mixture.FIELD_BREW_LOGS, BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_BREW, Brew.FIELD_ID}, brewIds)
                //.in(new String[] {Mixture.FIELD_BREW_LOGS, BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_BREW, Brew.FIELD_BATCH_ID}, brewBatchIds)
                //.in(new String[] {Mixture.FIELD_BREW_LOGS, BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_STATUS, BrewStageStatus.FIELD_ID}, stageStatusIds)
                //.in(new String[] {Mixture.FIELD_BREW_LOGS, BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_TASK, BrewTask.FIELD_ID}, stageTaskIds)
                //.in(new String[] {Mixture.FIELD_BREW_LOGS, BrewLog.FIELD_BREWSTAGE, BrewStage.FIELD_BREW, Brew.FIELD_PRODUCT, Product.FIELD_ID}, productIds)
                .build();
            
            Page<Mixture> mixturePage = mixtureRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return mixturePage;
	}

	@Override
	public Mixture getMixture(Long mixtureId) {
		Mixture mixture = mixtureRepository.findById(mixtureId).orElse(null);

        return mixture;
	}

	@Override
	public Mixture addMixture(Mixture mixture) {
		mixtureRepository.refresh(List.of(mixture));

		Mixture addedMixture = mixtureRepository.saveAndFlush(mixture);
	        
	    return addedMixture;
	}

	@Override
	public Mixture putMixture(Long mixtureId, Mixture putMixture) {
		mixtureRepository.refresh(List.of(putMixture));

		Mixture existingMixture = getMixture(mixtureId);          
        
        if (existingMixture == null) {
            existingMixture = putMixture;
            existingMixture.setId(mixtureId);
        } else {
        	existingMixture.optimisticLockCheck(putMixture);
            existingMixture.override(putMixture, getPropertyNames(BaseMixture.class));       
        }
        
        return mixtureRepository.saveAndFlush(existingMixture); 
	}


	@Override
	public Mixture patchMixture(Long mixtureId, Mixture patchMixture) {
        Mixture existingMixture = Optional.ofNullable(getMixture(mixtureId)).orElseThrow(() -> new EntityNotFoundException("Mixture", mixtureId.toString()));            
        
		mixtureRepository.refresh(List.of(patchMixture));
        
        existingMixture.optimisticLockCheck(patchMixture);
                     
        existingMixture.outerJoin(patchMixture, getPropertyNames(UpdateMixture.class));
                
        return mixtureRepository.saveAndFlush(existingMixture);
	}

	@Override
	public void deleteMixture(Long mixtureId) {
		mixtureRepository.deleteById(mixtureId);		
	}

	@Override
	public boolean mixtureExists(Long mixtureId) {
        return mixtureRepository.existsById(mixtureId);
	}

}
