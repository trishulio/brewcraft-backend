package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.FinishedGoodMixturePortion;

@Repository
public interface FinishedGoodMixturePortionRepository extends JpaRepository<FinishedGoodMixturePortion, Long>, JpaSpecificationExecutor<FinishedGoodMixturePortion> {
}
