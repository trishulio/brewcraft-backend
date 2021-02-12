package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class FacilityServiceImpl implements FacilityService {
    public static final Logger log = LoggerFactory.getLogger(FacilityServiceImpl.class);

    private FacilityRepository facilityRepository;
                    
    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }
    
    @Override
    public Page<FacilityEntity> getAllFacilities(int page, int size, Set<String> sort, boolean orderAscending) {        
        return facilityRepository.findAll(pageRequest(sort, orderAscending, page, size));
    }

    @Override
    public FacilityEntity getFacility(Long facilityId) {
        FacilityEntity facility = facilityRepository.findById(facilityId).orElse(null);
        
        return facility;      
    }

    @Override
    public FacilityEntity addFacility(FacilityEntity facility) {        
        return facilityRepository.save(facility);
    }
    
    @Override
    public FacilityEntity putFacility(Long facilityId, FacilityEntity updatedFacility) {        
        FacilityEntity facility = facilityRepository.findById(facilityId).orElse(null);
        
        if (facility != null) {
            updatedFacility.outerJoin(facility);
        }

        updatedFacility.setId(facilityId);
      
        return facilityRepository.save(updatedFacility);
    }
    
    @Override
    public FacilityEntity patchFacility(Long facilityId, FacilityEntity updatedFacility) {
        FacilityEntity facility = facilityRepository.findById(facilityId).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
     
        updatedFacility.outerJoin(facility);
        
        return facilityRepository.save(updatedFacility);
    }

    @Override
    public void deleteFacility(Long facilityId) {
        facilityRepository.deleteById(facilityId);        
    } 
    
    @Override
    public boolean facilityExists(Long facilityId) {
        return facilityRepository.existsById(facilityId);
    }

}
