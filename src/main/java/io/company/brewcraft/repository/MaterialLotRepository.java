package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.MaterialLot;

@Repository
public interface MaterialLotRepository extends JpaSpecificationExecutor<MaterialLot>, JpaRepository<MaterialLot, Long>, EnhancedMaterialLotRepository {
}
