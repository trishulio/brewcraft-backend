package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentAccessor;

public class EnhancedShipmentRepositoryImpl implements EnhancedShipmentRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedShipmentRepositoryImpl.class);

    private final AccessorRefresher<Long, ShipmentAccessor, Shipment> refresher;

    private final ShipmentStatusRepository statusRepo;
    private final MaterialLotRepository materialLotRepo;

    @Autowired
    public EnhancedShipmentRepositoryImpl(AccessorRefresher<Long, ShipmentAccessor, Shipment> refresher, ShipmentStatusRepository statusRepo, MaterialLotRepository materialLotRepo) {
        this.refresher = refresher;
        this.statusRepo = statusRepo;
        this.materialLotRepo = materialLotRepo;
    }

    @Override
    public void refresh(Collection<Shipment> shipments) {
        this.statusRepo.refreshAccessors(shipments);
        final List<MaterialLot> lots = shipments == null ? null : shipments.stream().filter(s -> s != null && s.getLots() != null && s.getLots().size() > 0).flatMap(s -> s.getLots().stream()).collect(Collectors.toList());
        this.materialLotRepo.refresh(lots);
    }

    @Override
    public void refreshAccessors(Collection<? extends ShipmentAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
