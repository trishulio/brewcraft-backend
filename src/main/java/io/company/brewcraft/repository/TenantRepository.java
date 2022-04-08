package io.company.brewcraft.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID>, JpaSpecificationExecutor<Tenant>, ExtendedRepository<UUID> {
    @Override
    @Query("select count(i) > 0 from tenant t where t.id in (:ids)")
    boolean existsByIds(Iterable<UUID> ids);

    @Override
    @Modifying
    @Query("delete from tenant t where t.id in (:ids)")
    int deleteByIds(Iterable<UUID> ids);
}
