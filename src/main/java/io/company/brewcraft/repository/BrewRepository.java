package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.Brew;

public interface BrewRepository extends JpaRepository<Brew, Long>, JpaSpecificationExecutor<Brew>, EnhancedBrewRepository {

}
