package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.EquipmentRepositoryGetAllEquipmentSpecification;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class EquipmentServiceImpl extends BaseService implements EquipmentService {
    private static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);
    
    private EquipmentRepository equipmentRepository;
    
    private FacilityService facilityService;
            
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, FacilityService facilityService) {
        this.equipmentRepository = equipmentRepository;
        this.facilityService = facilityService;
    }

    @Override
    public Page<Equipment> getAllEquipment(Set<Long> ids, Set<String> types, Set<String> statuses, Set<Long> facilityIds,
            int page, int size, Set<String> sort, boolean orderAscending) {
        Page<Equipment> equipmentPage = equipmentRepository.findAll(new EquipmentRepositoryGetAllEquipmentSpecification(ids, types, statuses, facilityIds), pageRequest(sort, orderAscending, page, size));

        return equipmentPage;
    }
    
    @Override
    public Equipment getEquipment(Long equipmentId) {       
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
       
        return equipment;
    }

    @Override
    public Equipment addEquipment(Long facilityId, Equipment equipment) { 
        Facility facility = Optional.ofNullable(facilityService.getFacility(facilityId)).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
        equipment.setFacility(facility);
    
        Equipment savedEntity = equipmentRepository.saveAndFlush(equipment); 
            
        return savedEntity;
    }

    @Override
    public Equipment putEquipment(Long facilityId, Long equipmentId, Equipment updatedEquipment) { 
        updatedEquipment.setId(equipmentId);
        
        return addEquipment(facilityId, updatedEquipment);
    }
    
    @Override
    public Equipment patchEquipment(Long equipmentId, Equipment updatedEquipment) {         
        Equipment existing = Optional.ofNullable(getEquipment(equipmentId)).orElseThrow(() -> new EntityNotFoundException("Equipment", equipmentId.toString()));

        updatedEquipment.copyToNullFields(existing);

        return addEquipment(existing.getFacility().getId(), updatedEquipment);
    }

    @Override
    public void deleteEquipment(Long equipmentId) {
        equipmentRepository.deleteById(equipmentId);        
    } 
    
    @Override
    public boolean equipmentExists(Long equipmentId) {
        return equipmentRepository.existsById(equipmentId);
    }
    
}
