package io.company.brewcraft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.SupplierContact;

public interface SupplierContactRepository extends JpaRepository<SupplierContact, Long>, JpaSpecificationExecutor<SupplierContact> {

    List<SupplierContact> findAllBySupplierId(Long supplierId);
}
