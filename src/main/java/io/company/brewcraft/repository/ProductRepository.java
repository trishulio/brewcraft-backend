package io.company.brewcraft.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  @Query("update PRODUCT p set p.deletedAt=CURRENT_DATE where p.id in (:ids)")
  @Modifying
  public void softDeleteByIds(@Param("ids") Set<Long> ids); 
  
  @Query("select p from PRODUCT p where p.id in (:ids) and p.deletedAt is NULL")
  public Optional<Product> findByIdsExcludeDeleted(@Param("ids") Set<Long> ids);
}
