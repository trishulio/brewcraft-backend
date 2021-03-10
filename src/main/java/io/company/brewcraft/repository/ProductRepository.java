package io.company.brewcraft.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

  @Query("update PRODUCT p set p.deletedAt=CURRENT_DATE where p.id in (:ids)")
  @Modifying
  public void softDeleteByIds(@Param("ids") Set<Long> ids); 
  
  @Query("select p from PRODUCT p where p.id in (:ids) and p.deletedAt is NULL")
  public Optional<ProductEntity> findByIdsExcludeDeleted(@Param("ids") Set<Long> ids);
}
