package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.UpdateMaterialPortion;
import io.company.brewcraft.repository.MaterialPortionRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class MaterialPortionServiceImpl extends BaseService implements MaterialPortionService {
    private static final Logger log = LoggerFactory.getLogger(MaterialPortionServiceImpl.class);
    
    private MaterialPortionRepository materialPortionRepository;
        
    public MaterialPortionServiceImpl(MaterialPortionRepository materialPortionRepository) {
        this.materialPortionRepository = materialPortionRepository;  
    }

	@Override
	public Page<MaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
		Specification<MaterialPortion> spec = SpecificationBuilder
                .builder()
                .in(MixtureRecording.FIELD_ID, ids)
                .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                .build();
            
            Page<MaterialPortion> materialPortionsPage = materialPortionRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return materialPortionsPage;
	}

	@Override
	public MaterialPortion getMaterialPortion(Long materialPortionId) {
		MaterialPortion materialPortion = materialPortionRepository.findById(materialPortionId).orElse(null);

		return materialPortion;
	}
	
	@Override
	public List<MaterialPortion> addMaterialPortions(List<MaterialPortion> materialPortions, Long mixtureId) {
		materialPortionRepository.refresh(materialPortions);

		List<MaterialPortion> addedMaterialPortions = materialPortionRepository.saveAll(materialPortions);
		materialPortionRepository.flush();
	        
	    return addedMaterialPortions;
	}

	@Override
	public MaterialPortion addMaterialPortion(MaterialPortion materialPortion, Long mixtureId) {
		materialPortionRepository.refresh(List.of(materialPortion));

		MaterialPortion addedMixture = materialPortionRepository.saveAndFlush(materialPortion);
	        
	    return addedMixture;
	}

	@Override
	public MaterialPortion putMaterialPortion(Long materialPortionId, MaterialPortion putMaterialPortion, Long mixtureId) {
		materialPortionRepository.refresh(List.of(putMaterialPortion));

		MaterialPortion existingMaterialPortion = getMaterialPortion(materialPortionId);          
        
        if (existingMaterialPortion == null) {
            existingMaterialPortion = putMaterialPortion;
            existingMaterialPortion.setId(materialPortionId);
        } else {
        	existingMaterialPortion.optimisticLockCheck(putMaterialPortion);
            existingMaterialPortion.override(putMaterialPortion, getPropertyNames(BaseMixtureRecording.class));       
        }
        
        return materialPortionRepository.saveAndFlush(existingMaterialPortion); 
	}


	@Override
	public MaterialPortion patchMaterialPortion(Long materialPortionId, MaterialPortion patchMaterialPortion) {
		MaterialPortion existingMaterialPortion = Optional.ofNullable(getMaterialPortion(materialPortionId)).orElseThrow(() -> new EntityNotFoundException("MaterialPortion", materialPortionId.toString()));            
        
		materialPortionRepository.refresh(List.of(patchMaterialPortion));
        
        existingMaterialPortion.optimisticLockCheck(patchMaterialPortion);
                     
        existingMaterialPortion.outerJoin(patchMaterialPortion, getPropertyNames(UpdateMaterialPortion.class));
                
        return materialPortionRepository.saveAndFlush(existingMaterialPortion);
	}

	@Override
	public void deleteMaterialPortion(Long materialPortionId) {
		materialPortionRepository.deleteById(materialPortionId);		
	}

	@Override
	public boolean materialPortionExists(Long materialPortionId) {
        return materialPortionRepository.existsById(materialPortionId);
	}

}
