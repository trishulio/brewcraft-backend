package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

}
