package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.SkuMaterial;

public interface SkuMaterialRepository extends JpaRepository<SkuMaterial, Long>, JpaSpecificationExecutor<SkuMaterial> {

}
