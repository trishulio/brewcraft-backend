package io.company.brewcraft.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import io.company.brewcraft.model.InvoiceStatus;

public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Long>, JpaSpecificationExecutor<InvoiceStatus> {

    @Query("select s from invoice_status s where s.name = :name")
    Optional<InvoiceStatus> findByName(String name);
    
    @Query("select s from invoice_status s where s.name in (:names)")
    Iterable<InvoiceStatus> findByNames(Set<String> names);
}
