package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
