package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateFacility;
import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.pojo.Facility;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.FacilityMapper;

@Transactional
public class FacilityServiceImpl extends BaseService implements FacilityService {
    public static final Logger log = LoggerFactory.getLogger(FacilityServiceImpl.class);
    
    private static final FacilityMapper facilityMapper = FacilityMapper.INSTANCE;

    private FacilityRepository facilityRepository;
                    
    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }
    
    @Override
    public Page<Facility> getAllFacilities(int page, int size, Set<String> sort, boolean orderAscending) {        
        Page<FacilityEntity> facilityPage = facilityRepository.findAll(pageRequest(sort, orderAscending, page, size));
    
        return facilityPage.map(facilityMapper::fromEntity);
    }

    @Override
    public Facility getFacility(Long facilityId) {
        FacilityEntity facility = facilityRepository.findById(facilityId).orElse(null);
        
        return facilityMapper.fromEntity(facility);      
    }

    @Override
    public Facility addFacility(Facility facility) { 
        
        FacilityEntity facilityEntity = facilityMapper.toEntity(facility);
        
        FacilityEntity addedFacility = facilityRepository.save(facilityEntity);
        
        return facilityMapper.fromEntity(addedFacility);      
    }
    
    @Override
    public Facility putFacility(Long facilityId, UpdateFacility updatedFacility) { 
        Facility existing = getFacility(facilityId);

        if (existing == null) {
            existing = new Facility();
            existing.setId(facilityId);
            existing.setCreatedAt(LocalDateTime.now()); // TODO: This is a hack. Need a fix at hibernate level to avoid any hibernate issues.
        }

        existing.override(updatedFacility, getPropertyNames(UpdateFacility.class));

        return addFacility(existing); 
    }
    
    @Override
    public Facility patchFacility(Long facilityId, UpdateFacility updatedFacility) {
        Facility existing = Optional.ofNullable(getFacility(facilityId)).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));

        existing.outerJoin(updatedFacility, getPropertyNames(UpdateFacility.class));

        return addFacility(existing);
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
