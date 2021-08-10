package io.company.brewcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> , EnhancedProductRepository {

  @Query("update PRODUCT p set p.deletedAt=CURRENT_TIMESTAMP where p.id in (:ids)")
  @Modifying
  public void softDeleteByIds(@Param("ids") Iterable<Long> ids);

  @Query("select p from PRODUCT p where p.id in (:ids) and p.deletedAt is NULL")
  // Shouldn't this return a LIST
  public Optional<Product> findByIdsExcludeDeleted(@Param("ids") Iterable<Long> ids);
}
