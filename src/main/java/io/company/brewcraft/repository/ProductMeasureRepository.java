package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.ProductMeasure;

public interface ProductMeasureRepository extends JpaRepository<ProductMeasure, String>, JpaSpecificationExecutor<ProductMeasure>, EnhancedProductMeasureRepository {

}
