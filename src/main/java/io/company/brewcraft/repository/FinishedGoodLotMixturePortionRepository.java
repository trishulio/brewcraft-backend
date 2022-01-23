package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.FinishedGoodLotMixturePortion;

@Repository
public interface FinishedGoodLotMixturePortionRepository extends JpaRepository<FinishedGoodLotMixturePortion, Long>, JpaSpecificationExecutor<FinishedGoodLotMixturePortion> {
}
