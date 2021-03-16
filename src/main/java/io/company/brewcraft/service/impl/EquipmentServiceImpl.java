package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateEquipment;
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.pojo.Equipment;
import io.company.brewcraft.pojo.Facility;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.EquipmentRepositoryGetAllEquipmentSpecification;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.EquipmentMapper;

@Transactional
public class EquipmentServiceImpl extends BaseService implements EquipmentService {
    public static final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);
    
    private static final EquipmentMapper equipmentMapper = EquipmentMapper.INSTANCE;

    private EquipmentRepository equipmentRepository;
    
    private FacilityService facilityService;
            
    public EquipmentServiceImpl(EquipmentRepository equipmentRepository, FacilityService facilityService) {
        this.equipmentRepository = equipmentRepository;
        this.facilityService = facilityService;
    }

    @Override
    public Page<Equipment> getAllEquipment(Set<Long> ids, Set<String> types, Set<String> statuses, Set<Long> facilityIds,
            int page, int size, Set<String> sort, boolean orderAscending) {
        
        Page<EquipmentEntity> equipmentPage = equipmentRepository.findAll(new EquipmentRepositoryGetAllEquipmentSpecification(ids, types, statuses, facilityIds), pageRequest(sort, orderAscending, page, size));

        return equipmentPage.map(equipment -> equipmentMapper.fromEntity(equipment, new CycleAvoidingMappingContext()));
    }
    
    @Override
    public Equipment getEquipment(Long equipmentId) {       
        EquipmentEntity equipmentEntity = equipmentRepository.findById(equipmentId).orElse(null);
        
        Equipment equipment = equipmentMapper.fromEntity(equipmentEntity, new CycleAvoidingMappingContext());
        
        return equipment;
    }

    @Override
    public Equipment addEquipment(Long facilityId, Equipment equipment) { 
        Facility facility = Optional.ofNullable(facilityService.getFacility(facilityId)).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
        equipment.setFacility(facility);
        
        EquipmentEntity equipmentEntity = equipmentMapper.toEntity(equipment, new CycleAvoidingMappingContext());

        EquipmentEntity savedEntity = equipmentRepository.saveAndFlush(equipmentEntity); 
            
        return equipmentMapper.fromEntity(savedEntity, new CycleAvoidingMappingContext());
    }

    @Override
    public Equipment putEquipment(Long facilityId, Long equipmentId, UpdateEquipment updatedEquipment) { 
        Equipment existing = getEquipment(equipmentId);

        if (existing == null) {
            existing = new Equipment();
            existing.setId(equipmentId);
            existing.setCreatedAt(LocalDateTime.now()); // TODO: This is a hack. Need a fix at hibernate level to avoid any hibernate issues.
        }

        existing.override(updatedEquipment, getPropertyNames(UpdateEquipment.class));
        
        return addEquipment(facilityId, existing);
    }
    
    @Override
    public Equipment patchEquipment(Long equipmentId, UpdateEquipment updatedEquipment) {         
        Equipment existing = Optional.ofNullable(getEquipment(equipmentId)).orElseThrow(() -> new EntityNotFoundException("Equipment", equipmentId.toString()));

        existing.outerJoin(updatedEquipment, getPropertyNames(UpdateEquipment.class));

        return addEquipment(existing.getFacility().getId(), existing);
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
