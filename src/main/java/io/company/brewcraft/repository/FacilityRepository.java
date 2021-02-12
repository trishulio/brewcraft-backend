package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.FacilityEntity;

public interface FacilityRepository extends JpaRepository<FacilityEntity, Long> {

}
