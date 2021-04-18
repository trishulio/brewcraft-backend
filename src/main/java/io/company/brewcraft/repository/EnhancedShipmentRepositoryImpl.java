package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;

public class EnhancedShipmentRepositoryImpl implements EnhancedShipmentRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedShipmentRepositoryImpl.class);

    private ShipmentStatusRepository statusRepo;
    private MaterialLotRepository materialLotRepo;

    @Autowired
    public EnhancedShipmentRepositoryImpl(ShipmentStatusRepository statusRepo, MaterialLotRepository materialLotRepo) {
        this.statusRepo = statusRepo;
        this.materialLotRepo = materialLotRepo;
    }

    @Override
    public void refresh(Collection<Shipment> shipments) {
        if (shipments != null && shipments.size() > 0) {
            this.statusRepo.refreshAccessors(shipments);

            List<MaterialLot> lots = shipments.stream().filter(s -> s.getLots() != null && s.getLots().size() > 0).flatMap(s -> s.getLots().stream()).collect(Collectors.toList());
            this.materialLotRepo.refresh(lots);
        }
    }
}
