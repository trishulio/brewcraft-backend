package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateFacility;
import io.company.brewcraft.pojo.Facility;

public interface FacilityService {

    public Page<Facility> getAllFacilities(int page, int size, Set<String> sort, boolean orderAscending);
    
    public Facility getFacility(Long id);

    public Facility addFacility(Facility facility);
    
    public Facility putFacility(Long id, UpdateFacility facility);
    
    public Facility patchFacility(Long id, UpdateFacility facility);

    public void deleteFacility(Long id);
    
    public boolean facilityExists(Long id);
        
}
