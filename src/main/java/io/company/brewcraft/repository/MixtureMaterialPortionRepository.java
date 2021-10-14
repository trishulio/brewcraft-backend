package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.MixtureMaterialPortion;

@Repository
public interface MixtureMaterialPortionRepository extends JpaRepository<MixtureMaterialPortion, Long>, JpaSpecificationExecutor<MixtureMaterialPortion>, EnhancedMixtureMaterialPortionRepository {

}
