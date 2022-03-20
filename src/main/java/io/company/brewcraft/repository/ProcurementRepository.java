package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;

@Repository
public interface ProcurementRepository extends JpaRepository<Procurement, ProcurementId>, JpaSpecificationExecutor<Procurement>, ExtendedRepository<ProcurementId> {
    @Override
    @Query("select count(i) > 0 from procurement p where p.id in (:ids)")
    boolean existsByIds(Iterable<ProcurementId> ids);

    @Override
    @Modifying
    @Query("delete from procurement p where p.id in (:ids)")
    long deleteByIds(Iterable<ProcurementId> ids);
}
