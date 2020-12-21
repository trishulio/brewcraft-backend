package io.company.brewcraft.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    }

    @Override
    public Page<Equipment> getAllEquipment(Set<Long> ids, Set<String> types, Set<String> statuses, 
            Set<Long> facilityIds, int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));
        
        return equipmentRepository.findAll(new EquipmentRepositoryGetAllEquipmentSpecification(ids, types, statuses, facilityIds), paging);
    }
    
    @Override
    public Equipment getEquipment(Long equipmentId) {       
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);

        return equipment;
    }

    @Override
    public void addEquipment(Long facilityId, Equipment equipment) {        
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
        
        equipment.setFacility(facility);
        
        equipmentRepository.save(equipment);  
    }

    @Override
    public void putEquipment(Long equipmentId, Equipment updatedEquipment) {        
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        
        if (equipment != null) {
            updatedEquipment.outerJoin(equipment);
        }
        
        updatedEquipment.setId(equipmentId);
        
        equipmentRepository.save(updatedEquipment);
    }
    
    @Override
    public void patchEquipment(Long equipmentId, Equipment updatedEquipment) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(() -> new EntityNotFoundException("Equipment", equipmentId.toString()));
     
        updatedEquipment.outerJoin(equipment);
        
        equipmentRepository.save(updatedEquipment);
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
