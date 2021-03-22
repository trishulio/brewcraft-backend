package io.company.brewcraft.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import io.company.brewcraft.model.ShipmentStatus;

public interface ShipmentStatusRepository extends CrudRepository<ShipmentStatus, Long> {
    
    @Query("select s from shipment_status s where s.name in (:names)")
    Iterable<ShipmentStatus> findByNames(Set<String> names);
}