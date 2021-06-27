package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder>, EnhancedPurchaseOrderRepository {
    @Query("select count(i) > 0 from purchase_order i where i.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);
}
