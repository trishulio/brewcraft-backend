package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.BrewStage;

public interface BrewStageRepository extends JpaRepository<BrewStage, Long>, JpaSpecificationExecutor<BrewStage> {

}
