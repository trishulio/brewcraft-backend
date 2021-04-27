package io.company.brewcraft.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice>, EnhancedInvoiceRepository {
    @Query("select count(i) > 0 from invoice i where i.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Modifying
    @Query("delete from invoice i where i.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);
}
