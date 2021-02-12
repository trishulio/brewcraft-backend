package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.EquipmentEntity;

public interface EquipmentService {

    public Page<EquipmentEntity> getAllEquipment(Set<Long> ids, Set<String> types,
            Set<String> statuses, Set<Long> facilityIds, int page, int size, Set<String> sort, boolean orderAscending);
    
    public EquipmentEntity getEquipment(Long equipmentId);

    public EquipmentEntity addEquipment(Long facilityId, EquipmentEntity equipment);
    
    public EquipmentEntity putEquipment(Long facilityId, Long equipmentId, EquipmentEntity equipment);
    
    public EquipmentEntity patchEquipment(Long equipmentId, EquipmentEntity equipment);

    public void deleteEquipment(Long equipmentId);
    
    public boolean equipmentExists(Long equipmentId);
    
}
