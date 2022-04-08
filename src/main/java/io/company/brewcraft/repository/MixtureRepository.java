package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.Mixture;

public interface MixtureRepository extends JpaRepository<Mixture, Long>, JpaSpecificationExecutor<Mixture> {
}
