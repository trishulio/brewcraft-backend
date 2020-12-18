package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Facility;

public interface FacilityService {

    public Page<Facility> getAllFacilities(int page, int size, String[] sort, boolean order_asc);
    
    public Facility getFacility(Long id);

    public void addFacility(Facility facility);
    
    public void putFacility(Long id, Facility facility);
    
    public void patchFacility(Long id, Facility facility);

    public void deleteFacility(Long id);
    
    public boolean facilityExists(Long id);
        
}
