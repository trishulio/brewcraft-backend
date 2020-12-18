package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Equipment;

public interface EquipmentService {

    public Page<Equipment> getAllEquipment(Set<Long> ids, Set<String> types,
            Set<String> statuses, Set<Long> facilityIds, int page, int size, String[] sort, boolean order_asc);
    
    public Equipment getEquipment(Long equipmentId);

    public void addEquipment(Long facilityId, Equipment equipment);
    
    public void putEquipment(Long equipmentId, Equipment equipment);
    
    public void patchEquipment(Long equipmentId, Equipment equipment);

    public void deleteEquipment(Long equipmentId);
    
    public boolean equipmentExists(Long equipmentId);
    
}
