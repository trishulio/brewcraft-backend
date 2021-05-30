package io.company.brewcraft.service;

import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Facility;

public interface FacilityService {

    public Page<Facility> getAllFacilities(int page, int size, SortedSet<String> sort, boolean orderAscending);
    
    public Facility getFacility(Long id);

    public Facility addFacility(Facility facility);
    
    public Facility putFacility(Long id, Facility facility);
    
    public Facility patchFacility(Long id, Facility facility);

    public void deleteFacility(Long id);
    
    public boolean facilityExists(Long id);
        
}
