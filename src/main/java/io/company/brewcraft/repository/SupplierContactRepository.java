package io.company.brewcraft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.company.brewcraft.model.SupplierContactEntity;

public interface SupplierContactRepository extends JpaRepository<SupplierContactEntity, Long> {

    List<SupplierContactEntity> findAllBySupplierId(Long supplierId);
}
