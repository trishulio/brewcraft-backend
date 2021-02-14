package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long>, JpaSpecificationExecutor<MaterialEntity> {

}
