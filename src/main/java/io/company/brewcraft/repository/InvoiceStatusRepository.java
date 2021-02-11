package io.company.brewcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.InvoiceStatusEntity;

public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatusEntity, Long>, JpaSpecificationExecutor<InvoiceStatusEntity> {

    Optional<InvoiceStatusEntity> findByName(String name);
}
