package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.SupplierEntity;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

}
