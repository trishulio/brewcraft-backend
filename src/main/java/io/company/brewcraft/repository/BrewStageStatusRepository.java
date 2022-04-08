package io.company.brewcraft.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.BrewStageStatus;

public interface BrewStageStatusRepository extends JpaRepository<BrewStageStatus, Long>, JpaSpecificationExecutor<BrewStageStatus> {
    @Query("select s from BREW_STAGE_STATUS s where s.name = :name")
    Optional<BrewStageStatus> findByName(@Param("name") String name);

    @Query("select s from BREW_STAGE_STATUS s where s.name in (:names)")
    Iterable<BrewStageStatus> findByNames(@Param("names") Set<String> names);
}
