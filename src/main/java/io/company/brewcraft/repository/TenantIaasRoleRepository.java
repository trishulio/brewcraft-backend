package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.TenantIaasRole;

public interface TenantIaasRoleRepository extends JpaRepository<TenantIaasRole, Long>, JpaSpecificationExecutor<TenantIaasRole>, ExtendedRepository<Long> {
    // TODO:
}
