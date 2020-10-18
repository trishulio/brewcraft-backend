package io.company.brewcraft.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {

}
