package io.company.brewcraft.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.company.brewcraft.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>, JpaSpecificationExecutor<Shipment>, EnhancedShipmentRepository {
    @Query("select count(s) > 0 from shipment s where s.id in (:ids)")
    boolean existsByIds(Collection<Long> ids);

    @Modifying
    @Query("delete from shipment s where s.id in (:ids)")
    int deleteByIds(Collection<Long> ids);
}
