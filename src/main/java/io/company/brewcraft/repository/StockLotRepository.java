package io.company.brewcraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.StockLot;

public interface StockLotRepository extends JpaRepository<StockLot, Long>, JpaSpecificationExecutor<StockLot> {
}
