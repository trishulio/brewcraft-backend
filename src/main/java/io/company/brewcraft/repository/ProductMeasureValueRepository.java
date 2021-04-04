package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.ProductMeasureValue;

public interface ProductMeasureValueRepository extends JpaRepository<ProductMeasureValue, Long>, JpaSpecificationExecutor<ProductMeasureValue> {

}
