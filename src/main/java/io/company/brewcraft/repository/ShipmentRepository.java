package io.company.brewcraft.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.company.brewcraft.model.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, Long>, JpaSpecificationExecutor<Shipment>, EnhancedShipmentRepository {
    
    @Query("select count(s) > 0 from SHIPMENT s where s.id in (:ids)")
    boolean existsByIds(Collection<Long> ids);

    @Modifying
    @Query("delete from SHIPMENT s where s.id in (:ids)")
    int deleteByIds(Collection<Long> ids);
}
