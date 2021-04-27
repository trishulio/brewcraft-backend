package io.company.brewcraft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.company.brewcraft.model.ShipmentStatus;

public interface ShipmentStatusRepository extends JpaRepository<ShipmentStatus, Long>, EnhancedShipmentStatusRepository {
    @Query("select s from shipment_status s where s.name in (:names)")
    List<ShipmentStatus> findByNames(@Param("names") Iterable<String> names);

    @Query("select s from shipment_status s where s.name = :name")
    Optional<ShipmentStatus> findByName(@Param("name") String name);
}