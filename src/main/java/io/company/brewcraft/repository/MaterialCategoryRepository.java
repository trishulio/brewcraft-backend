package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.MaterialCategoryEntity;

public interface MaterialCategoryRepository extends JpaRepository<MaterialCategoryEntity, Long>, JpaSpecificationExecutor<MaterialCategoryEntity> {

}
