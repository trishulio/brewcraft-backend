package io.company.brewcraft.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.BrewTask;

public interface BrewTaskRepository extends JpaRepository<BrewTask, Long>, JpaSpecificationExecutor<BrewTask>, EnhancedBrewTaskRepository {

    @Query("select s from BREW_TASK s where s.name = :name")
    Optional<BrewTask> findByName(@Param("name") String name);
    
    @Query("select s from BREW_TASK s where s.name in (:names)")
    Iterable<BrewTask> findByNames(@Param("names") Set<String> names);
}
