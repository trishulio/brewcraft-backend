package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.Sku;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long>, JpaSpecificationExecutor<Sku>, ExtendedRepository<Long> {
    @Override
    @Query("select count(i) > 0 from SKU i where i.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from SKU i where i.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);
}
