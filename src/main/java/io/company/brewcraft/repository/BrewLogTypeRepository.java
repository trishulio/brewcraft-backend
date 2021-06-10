package io.company.brewcraft.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.BrewLogType;

public interface BrewLogTypeRepository extends JpaRepository<BrewLogType, Long>, JpaSpecificationExecutor<BrewLogType>, EnhancedBrewLogTypeRepository {

    @Query("select s from BREW_LOG_TYPE s where s.name = :name")
    Optional<BrewLogType> findByName(@Param("name") String name);
    
    @Query("select s from BREW_LOG_TYPE s where s.name in (:names)")
    Iterable<BrewLogType> findByNames(@Param("names") Set<String> names);
}
