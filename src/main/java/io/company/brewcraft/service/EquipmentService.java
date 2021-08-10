package io.company.brewcraft.service;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Equipment;

public interface EquipmentService {

    public Page<Equipment> getAllEquipment(Set<Long> ids, Set<String> types,
            Set<String> statuses, Set<Long> facilityIds, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public Equipment getEquipment(Long equipmentId);

    public Equipment addEquipment(Long facilityId, Equipment equipment);

    public Equipment putEquipment(Long facilityId, Long equipmentId, Equipment equipment);

    public Equipment patchEquipment(Long equipmentId, Equipment equipment);

    public void deleteEquipment(Long equipmentId);

    public boolean equipmentExists(Long equipmentId);

}
