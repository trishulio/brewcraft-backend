package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.FacilityEntity;

public interface FacilityService {

    public Page<FacilityEntity> getAllFacilities(int page, int size, Set<String> sort, boolean orderAscending);
    
    public FacilityEntity getFacility(Long id);

    public FacilityEntity addFacility(FacilityEntity facility);
    
    public FacilityEntity putFacility(Long id, FacilityEntity facility);
    
    public FacilityEntity patchFacility(Long id, FacilityEntity facility);

    public void deleteFacility(Long id);
    
    public boolean facilityExists(Long id);
        
}
