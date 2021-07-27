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

import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.UpdateMixtureRecording;
import io.company.brewcraft.repository.MixtureRecordingRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class MixtureRecordingServiceImpl extends BaseService implements MixtureRecordingService {
    private static final Logger log = LoggerFactory.getLogger(MixtureRecordingServiceImpl.class);
    
    private MixtureRecordingRepository mixtureRecordingRepository;
    
    private MixtureService mixtureService;
        
    public MixtureRecordingServiceImpl(MixtureRecordingRepository mixtureRecordingRepository, MixtureService mixtureService) {
        this.mixtureRecordingRepository = mixtureRecordingRepository;  
        this.mixtureService = mixtureService;
    }

	@Override
	public Page<MixtureRecording> getMixtureRecordings(Set<Long> ids, Set<Long> mixtureIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
		Specification<MixtureRecording> spec = SpecificationBuilder
                .builder()
                .in(MixtureRecording.FIELD_ID, ids)
                .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                .build();
            
            Page<MixtureRecording> mixtureRecordingPage = mixtureRecordingRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return mixtureRecordingPage;
	}

	@Override
	public MixtureRecording getMixtureRecording(Long mixtureRecordingId) {
		MixtureRecording mixtureRecording = mixtureRecordingRepository.findById(mixtureRecordingId).orElse(null);

        return mixtureRecording;
	}
	
	@Override
	public List<MixtureRecording> addMixtureRecordings(List<MixtureRecording> mixtureRecordings) {		
		mixtureRecordingRepository.refresh(mixtureRecordings);

		List<MixtureRecording> addedMixtureRecordings = mixtureRecordingRepository.saveAll(mixtureRecordings);
		mixtureRecordingRepository.flush();
	        
	    return addedMixtureRecordings;
	}

	@Override
	public MixtureRecording addMixtureRecording(MixtureRecording mixtureRecording) {
		mixtureRecordingRepository.refresh(List.of(mixtureRecording));

		MixtureRecording addedMixture = mixtureRecordingRepository.saveAndFlush(mixtureRecording);
	        
	    return addedMixture;
	}

	@Override
	public MixtureRecording putMixtureRecording(Long mixtureRecordingId, MixtureRecording putMixtureRecording) {		
		mixtureRecordingRepository.refresh(List.of(putMixtureRecording));

		MixtureRecording existingMixtureRecording = getMixtureRecording(mixtureRecordingId);          
        
        if (existingMixtureRecording == null) {
            existingMixtureRecording = putMixtureRecording;
            existingMixtureRecording.setId(mixtureRecordingId);
        } else {
        	existingMixtureRecording.optimisticLockCheck(putMixtureRecording);
            existingMixtureRecording.override(putMixtureRecording, getPropertyNames(BaseMixtureRecording.class));       
        }
                
        return mixtureRecordingRepository.saveAndFlush(existingMixtureRecording); 
	}


	@Override
	public MixtureRecording patchMixtureRecording(Long mixtureRecordingId, MixtureRecording patchMixtureRecording) {
		MixtureRecording existingMixtureRecording = Optional.ofNullable(getMixtureRecording(mixtureRecordingId)).orElseThrow(() -> new EntityNotFoundException("MixtureRecording", mixtureRecordingId.toString()));            
        
		mixtureRecordingRepository.refresh(List.of(patchMixtureRecording));
        
        existingMixtureRecording.optimisticLockCheck(patchMixtureRecording);
                     
        existingMixtureRecording.outerJoin(patchMixtureRecording, getPropertyNames(UpdateMixtureRecording.class));
                
        Mixture mixture = Optional.ofNullable(mixtureService.getMixture(existingMixtureRecording.getMixture().getId())).orElseThrow(() -> new EntityNotFoundException("Mixture", existingMixtureRecording.getMixture().getId().toString()));
        existingMixtureRecording.setMixture(mixture);
        
        return mixtureRecordingRepository.saveAndFlush(existingMixtureRecording);
	}

	@Override
	public void deleteMixtureRecording(Long mixtureRecordingId) {
		mixtureRecordingRepository.deleteById(mixtureRecordingId);		
	}

	@Override
	public boolean mixtureRecordingExists(Long mixtureRecordingId) {
        return mixtureRecordingRepository.existsById(mixtureRecordingId);
	}

}
