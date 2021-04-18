package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.InvoiceStatus;

@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Long>, JpaSpecificationExecutor<InvoiceStatus>, EnhancedInvoiceStatusRepository {

    @Query("select s from invoice_status s where s.name = :name")
    Optional<InvoiceStatus> findByName(@Param("name") String name);
    
    @Query("select s from invoice_status s where s.name in (:names)")
    Collection<InvoiceStatus> findByNames(@Param("names") Set<String> names);
}
