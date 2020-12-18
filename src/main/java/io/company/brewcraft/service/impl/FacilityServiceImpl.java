package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Facility;
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
    public Page<Facility> getAllFacilities(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));
        
        return facilityRepository.findAll(paging);
    }

    @Override
    public Facility getFacility(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElse(null);
        
        return facility;      
    }

    @Override
    public void addFacility(Facility facility) {        
        facilityRepository.save(facility);
    }
    
    @Override
    public void putFacility(Long facilityId, Facility updatedFacility) {        
        Facility facility = facilityRepository.findById(facilityId).orElse(null);
        
        if (facility != null) {
            updatedFacility.outerJoin(facility);
        }

        updatedFacility.setId(facilityId);
      
        facilityRepository.save(updatedFacility);
    }
    
    @Override
    public void patchFacility(Long facilityId, Facility updatedFacility) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
     
        updatedFacility.outerJoin(facility);
        
        facilityRepository.save(updatedFacility);
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
