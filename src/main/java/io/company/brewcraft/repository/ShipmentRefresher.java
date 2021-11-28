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
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.MaterialLotAccessor;
import io.company.brewcraft.service.ShipmentStatusAccessor;

public class ShipmentRefresher implements Refresher<Shipment, ShipmentAccessor> {
    private static final Logger log = LoggerFactory.getLogger(ShipmentRefresher.class);

    private final AccessorRefresher<Long, ShipmentAccessor, Shipment> refresher;

    private final Refresher<ShipmentStatus, ShipmentStatusAccessor> shipmentStatusRefresher;
    private final Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher;

    @Autowired
    public ShipmentRefresher(AccessorRefresher<Long, ShipmentAccessor, Shipment> refresher, Refresher<ShipmentStatus, ShipmentStatusAccessor> shipmentStatusRefresher, Refresher<MaterialLot, MaterialLotAccessor> materialLotRefresher) {
        this.refresher = refresher;
        this.shipmentStatusRefresher = shipmentStatusRefresher;
        this.materialLotRefresher = materialLotRefresher;
    }

    @Override
    public void refresh(Collection<Shipment> shipments) {
        this.shipmentStatusRefresher.refreshAccessors(shipments);
        final List<MaterialLot> lots = shipments == null ? null : shipments.stream().filter(s -> s != null && s.getLotCount() > 0).flatMap(s -> s.getLots().stream()).collect(Collectors.toList());
        this.materialLotRefresher.refresh(lots);
    }

    @Override
    public void refreshAccessors(Collection<? extends ShipmentAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
