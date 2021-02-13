package io.company.brewcraft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.StorageEntity;

public interface StorageRepository extends JpaRepository<StorageEntity, Long> {

    List<StorageEntity> findAllByFacilityId(Long facilityId);
    
}
