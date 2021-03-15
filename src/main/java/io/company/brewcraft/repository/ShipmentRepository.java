package io.company.brewcraft.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import io.company.brewcraft.pojo.Shipment;

public interface ShipmentRepository extends CrudRepository<Shipment, Long>, JpaSpecificationExecutor<Shipment>, EnhancedShipmentRepository {
    boolean existsByIds(Collection<Long> ids);
    
    int softDelete(Collection<Long> ids);
}
