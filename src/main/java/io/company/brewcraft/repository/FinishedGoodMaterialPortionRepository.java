package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;

@Repository
public interface FinishedGoodMaterialPortionRepository extends JpaRepository<FinishedGoodMaterialPortion, Long>, JpaSpecificationExecutor<FinishedGoodMaterialPortion>, EnhancedFinishedGoodMaterialPortionRepository {
}
