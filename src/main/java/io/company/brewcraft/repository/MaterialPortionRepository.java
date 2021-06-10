package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.MaterialPortion;

@Repository
public interface MaterialPortionRepository extends JpaRepository<MaterialPortion, Long>, JpaSpecificationExecutor<MaterialPortion>, EnhancedMaterialPortionRepository {

}
