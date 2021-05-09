package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class FacilityServiceImpl extends BaseService implements FacilityService {
    private static final Logger log = LoggerFactory.getLogger(FacilityServiceImpl.class);
    
    private FacilityRepository facilityRepository;
                    
    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }
    
    @Override
    public Page<Facility> getAllFacilities(int page, int size, Set<String> sort, boolean orderAscending) {        
        Page<Facility> facilityPage = facilityRepository.findAll(pageRequest(sort, orderAscending, page, size));
    
        return facilityPage;
    }

    @Override
    public Facility getFacility(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElse(null);
        
        return facility;      
    }

    @Override
    public Facility addFacility(Facility facility) {         
        Facility addedFacility = facilityRepository.saveAndFlush(facility);
        
        return addedFacility;    
    }
    
    @Override
    public Facility putFacility(Long facilityId, Facility updatedFacility) { 
        updatedFacility.setId(facilityId);
        
        return addFacility(updatedFacility); 
    }
    
    @Override
    public Facility patchFacility(Long facilityId, Facility updatedFacility) {
        Facility existingFacility = Optional.ofNullable(getFacility(facilityId)).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));

        updatedFacility.copyToNullFields(existingFacility);

        return addFacility(updatedFacility);
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
