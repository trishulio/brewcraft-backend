package io.company.brewcraft.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.validator.Validator;

public class ShipmentService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    private ShipmentRepository repo;
    private ShipmentItemService itemService;

    public ShipmentService(ShipmentRepository repo, ShipmentItemService itemService) {
        this.repo = repo;
        this.itemService = itemService;
    }

    public Collection<Shipment> getShipments() {
        return null;
    }

    public Shipment getShipment(Validator validator, Long id) {
        validator.rule(id != null, "Non-null Id expected");
        validator.raiseErrors();

        Shipment shipment = null;

        log.debug("Finding shipment by Id: {}", id);
        Optional<Shipment> retrieved = repo.findById(id);

        if (retrieved.isPresent()) {
            log.debug("Shipment not found with id: {}", id);
            shipment = retrieved.get();
        }

        return shipment;
    }

    public boolean existsByIds(Validator validator, Collection<Long> ids) {
        validator.rule(ids != null, "");
        validator.raiseErrors();

        log.debug("Search shipment exists in Id: {}", ids);
        boolean exists = repo.existsByIds(ids);
        log.debug("Shipments exists: {}", exists);
        
        return exists;
    }

    public int delete(Validator validator, Collection<Long> ids) {
        validator.rule(ids != null, "Cannot retrieve Ids to delete from a null collection");
        validator.raiseErrors();

        log.debug("Attempting to delete shipments with Ids: {}", ids);
        int count = repo.deleteByIds(ids);
        log.debug("Number of shipments deleted: {}", count);
        
        return count;
    }

    public Shipment add(Validator validator, Long invoiceId, Shipment shipment) {
        validator.rule(invoiceId != null, "InvoiceId cannot be null");
        validator.rule(shipment != null, "Shipment cannot be null");
        validator.raiseErrors();

        Shipment addition = new Shipment();
        Collection<ShipmentItem> itemAdditions = itemService.getAddItems(validator, shipment.getItems());

        addition.override(shipment, getPropertyNames(BaseShipment.class));
        addition.setItems(itemAdditions);

        return repo.save(invoiceId, addition);
    }

    public Shipment put(Validator validator, Long invoiceId, Long shipmentId, Shipment update) {
        validator.rule(invoiceId != null, "InvoiceId cannot be null");
        validator.rule(update != null, "Shipment cannot be null");
        validator.raiseErrors();

        Class<?> shipmentClz = UpdateShipment.class;
        Shipment existing = getShipment(validator, shipmentId);
        if (existing == null) {
            existing = new Shipment();
//            existing.setCreatedAt(now());

            shipmentClz = BaseShipment.class;
        }
        existing.setId(shipmentId);

        Collection<ShipmentItem> existingItems = existing.getItems();
        Collection<ShipmentItem> updatedItems = itemService.getPutItems(validator, existingItems, update.getItems());
        existing.override(update, getPropertyNames(shipmentClz));
        existing.setItems(updatedItems);

        return repo.save(invoiceId, existing);
    }

    public Shipment patch(Validator validator, Long invoiceId, Long shipmentId, Shipment update) {
        validator.rule(invoiceId != null, "InvoiceId cannot be null");
        validator.rule(update != null, "Shipment cannot be null");
        validator.raiseErrors();

        Shipment existing = repo.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment", shipmentId));

        Collection<ShipmentItem> existingItems = existing.getItems();
        Collection<ShipmentItem> updatedItems = itemService.getPatchItems(validator, existingItems, update.getItems());
        existing.outerJoin(update, getPropertyNames(UpdateShipment.class));
        existing.setItems(updatedItems);

        return repo.save(invoiceId, existing);
    }
}
