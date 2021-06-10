package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.BrewLog;

public interface BrewLogRepository extends JpaRepository<BrewLog, Long>, JpaSpecificationExecutor<BrewLog>, EnhancedBrewLogRepository {

}
