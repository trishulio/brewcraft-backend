package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class ShipmentService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    private ShipmentRepository repo;
    private ShipmentItemService itemService;

    public ShipmentService(ShipmentRepository repo, ShipmentItemService itemService) {
        this.repo = repo;
        this.itemService = itemService;
    }

    public Page<Shipment> getShipments(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> shipmentNumbers,
        Set<String> lotNumbers,
        Set<String> descriptions,
        Set<String> statuses,
        Set<Long> invoiceIds,
        LocalDateTime deliveryDueDateFrom,
        LocalDateTime deliveryDueDateTo,
        LocalDateTime deliveredDateFrom,
        LocalDateTime deliveredDateTo,
        Set<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        Specification<Shipment> spec= SpecificationBuilder
                                                        .builder()
                                                        .in(Shipment.FIELD_ID, ids)
                                                        .not().in(Shipment.FIELD_ID, excludeIds)
                                                        .in(Shipment.FIELD_SHIPMENT_NUMBER, shipmentNumbers)
                                                        .in(Shipment.FIELD_LOT_NUMBER, lotNumbers)
                                                        .in(Shipment.FIELD_DESCRIPTION, descriptions)
                                                        .in(new String[] {Shipment.FIELD_STATUS, ShipmentStatus.FIELD_NAME}, statuses)
                                                        .in(new String[] {Shipment.FIELD_INVOICE, Invoice.FIELD_ID}, invoiceIds)
                                                        .between(Shipment.FIELD_DELIVERY_DUE_DATE, deliveryDueDateFrom, deliveryDueDateTo)
                                                        .between(Shipment.FIELD_DELIVERED_DATE, deliveredDateFrom, deliveredDateTo)
                                                        .build();
        return repo.findAll(spec, pageRequest(sort, orderAscending, page, size));
    }

    public Shipment getShipment(Long id) {
        log.debug("Fetching shipment with Id: {}", id);

        Shipment shipment = null;

        log.debug("Finding shipment by Id: {}", id);
        Optional<Shipment> retrieved = repo.findById(id);

        if (retrieved.isPresent()) {
            log.debug("Shipment found with id: {}", id);
            shipment = retrieved.get();
        }

        return shipment;
    }

    public boolean existsByIds(Collection<Long> ids) {
        log.debug("Checking invoice exists in Ids: {}", ids);
        boolean exists = repo.existsByIds(ids);
        log.debug("Shipments exists: {}", exists);
        
        return exists;
    }

    public int delete(Collection<Long> ids) {
        log.debug("Attempting to delete shipments with Ids: {}", ids);
        int count = repo.deleteByIds(ids);
        log.debug("Number of shipments deleted: {}", count);
        
        return count;
    }

    public Shipment add(Long invoiceId, Shipment shipment) {
        log.debug("Adding a new shipment under invoice: {} with {} items", invoiceId, shipment.getItems() == null ? null : shipment.getItems().size());
        Shipment addition = new Shipment();
        List<ShipmentItem> itemAdditions = itemService.getAddItems(shipment.getItems());
        log.debug("Number of item additions: {}", itemAdditions == null ? null : itemAdditions.size());

        addition.override(shipment, getPropertyNames(BaseShipment.class));
        addition.setItems(itemAdditions);

        repo.refresh(invoiceId, addition);

        return repo.saveAndFlush(addition);
    }

    public Shipment put(Long invoiceId, Long shipmentId, Shipment update) {
        log.debug("Putting a shipment with Id: {} under InvoiceId: {}", shipmentId, invoiceId);

        Shipment existing = getShipment(shipmentId);
        Class<? super Shipment> shipmentClz = BaseShipment.class;

        if (existing == null) {
            existing = new Shipment(shipmentId); //TODO: Hibernate ignores the id.
        } else {
            existing.optimisicLockCheck(update);
            shipmentClz = UpdateShipment.class;
        }

        log.debug("Shipment with Id: {} has {} existing items", existing == null ? null : invoiceId, existing.getItems() == null ? null : existing.getItems().size());
        log.debug("Update payload has {} item updates", update.getItems() == null ? null : update.getItems().size());

        List<ShipmentItem> updatedItems = itemService.getPutItems(existing.getItems(), update.getItems());
        log.debug("Total UpdateItems: {}", updatedItems == null ? null : updatedItems.size());

        Shipment temp = new Shipment();
        temp.override(update, getPropertyNames(shipmentClz));
        temp.setItems(updatedItems);
        repo.refresh(invoiceId, temp);

        existing.override(temp, getPropertyNames(shipmentClz));

        return repo.saveAndFlush(existing);
    }

    public Shipment patch(Long invoiceId, Long shipmentId, Shipment update) {
        log.debug("Performing patch on Shipment with Id: {}", invoiceId);

        Shipment existing = repo.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment", shipmentId));
        existing.optimisicLockCheck(update);
        
        if (invoiceId == null && existing.getInvoice() != null) {
            invoiceId = existing.getInvoice().getId();
        }

        List<ShipmentItem> existingItems = existing.getItems();
        List<ShipmentItem> updatedItems = itemService.getPatchItems(existingItems, update.getItems());

        Shipment temp = new Shipment();
        temp.override(existing);
        temp.outerJoin(update, getPropertyNames(UpdateShipment.class));
        temp.setItems(updatedItems);        
        repo.refresh(invoiceId, temp);

        existing.override(temp, getPropertyNames(UpdateShipment.class));

        return repo.saveAndFlush(existing);
    }
}
