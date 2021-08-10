package io.company.brewcraft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long>, EnhancedStorageRepository {

    List<Storage> findAllByFacilityId(Long facilityId);

}
