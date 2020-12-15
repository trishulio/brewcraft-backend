package io.company.brewcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.UnitEntity;

@Repository
@Transactional
public interface QuantityUnitRepository extends JpaRepository<UnitEntity, Long>, JpaSpecificationExecutor<UnitEntity> {
    Optional<UnitEntity> findBySymbol(String symbol);
}
