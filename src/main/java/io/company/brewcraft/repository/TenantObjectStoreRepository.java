package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.TenantObjectStore;

public interface TenantObjectStoreRepository extends JpaRepository<TenantObjectStore, Long>, JpaSpecificationExecutor<TenantObjectStore>, ExtendedRepository<Long> {

}
