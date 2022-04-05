package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.MixtureMaterialPortion;

@Repository
public interface MixtureMaterialPortionRepository extends JpaRepository<MixtureMaterialPortion, Long>, JpaSpecificationExecutor<MixtureMaterialPortion>, ExtendedRepository<Long> {

    @Override
    @Query("select count(i) > 0 from MIXTURE_MATERIAL_PORTION i where i.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from MIXTURE_MATERIAL_PORTION i where i.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);

}
