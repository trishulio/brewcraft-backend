package io.company.brewcraft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.pojo.InvoiceStatus;

public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Long>, JpaSpecificationExecutor<InvoiceStatus> {

    Optional<InvoiceStatus> findByName(String name);
}
