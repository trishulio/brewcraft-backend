package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.EquipmentRepositoryGetAllEquipmentSpecification;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    public static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private EquipmentRepository equipmentRepository;
    
    private FacilityRepository facilityRepository;
            
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, FacilityRepository facilityRepository) {
        this.equipmentRepository = equipmentRepository;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public Page<Equipment> getAllEquipment(Set<Long> ids, Set<String> types, Set<String> statuses, Set<Long> facilityIds,
            int page, int size, Set<String> sort, boolean orderAscending) {
        
        return equipmentRepository.findAll(new EquipmentRepositoryGetAllEquipmentSpecification(ids, types, statuses, facilityIds), pageRequest(sort, orderAscending, page, size));
    }
    
    @Override
    public Equipment getEquipment(Long equipmentId) {       
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);

        return equipment;
    }

    @Override
    public Equipment addEquipment(Long facilityId, Equipment equipment) { 
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
        equipment.setFacility(facility);
        
        return equipmentRepository.save(equipment);  
    }

    @Override
    public Equipment putEquipment(Long facilityId, Long equipmentId, Equipment updatedEquipment) {        
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        
        if (equipment != null) {
            updatedEquipment.outerJoin(equipment);
        }
        
        updatedEquipment.setId(equipmentId);
        
        return addEquipment(facilityId, updatedEquipment);
    }
    
    @Override
    public Equipment patchEquipment(Long equipmentId, Equipment updatedEquipment) {         
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(() -> new EntityNotFoundException("Equipment", equipmentId.toString()));
     
        updatedEquipment.outerJoin(equipment);
        
        return equipmentRepository.save(updatedEquipment);
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
