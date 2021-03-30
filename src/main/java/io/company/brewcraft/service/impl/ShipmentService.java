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
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class ShipmentService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    private ShipmentRepository repo;
    private ShipmentItemService itemService;
    private UtilityProvider utilProvider;

    public ShipmentService(ShipmentRepository repo, ShipmentItemService itemService, UtilityProvider utilProvider) {
        this.repo = repo;
        this.itemService = itemService;
        this.utilProvider = utilProvider;
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
        Validator validator = this.utilProvider.getValidator();
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

    public boolean existsByIds(Collection<Long> ids) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(ids != null, "");
        validator.raiseErrors();

        log.debug("Search shipment exists in Id: {}", ids);
        boolean exists = repo.existsByIds(ids);
        log.debug("Shipments exists: {}", exists);
        
        return exists;
    }

    public int delete(Collection<Long> ids) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(ids != null, "Cannot retrieve Ids to delete from a null collection");
        validator.raiseErrors();

        log.debug("Attempting to delete shipments with Ids: {}", ids);
        int count = repo.deleteByIds(ids);
        log.debug("Number of shipments deleted: {}", count);
        
        return count;
    }

    public Shipment add(Long invoiceId, Shipment shipment) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(shipment != null, "Shipment cannot be null");
        validator.raiseErrors();

        Shipment addition = new Shipment();
        List<ShipmentItem> itemAdditions = itemService.getAddItems(shipment.getItems());

        addition.override(shipment, getPropertyNames(BaseShipment.class));
        addition.setItems(itemAdditions);

        return repo.save(invoiceId, addition);
    }

    public Shipment put(Long invoiceId, Long shipmentId, Shipment update) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(update != null, "Shipment cannot be null");
        validator.raiseErrors();

        Class<?> shipmentClz = UpdateShipment.class;
        Shipment existing = getShipment(shipmentId);
        if (existing == null) {
            existing = new Shipment();
//            existing.setCreatedAt(now());

            shipmentClz = BaseShipment.class;
        }
        existing.setId(shipmentId);

        List<ShipmentItem> existingItems = existing.getItems();
        List<ShipmentItem> updatedItems = itemService.getPutItems(existingItems, update.getItems());
        existing.override(update, getPropertyNames(shipmentClz));
        existing.setItems(updatedItems);

        return repo.save(invoiceId, existing);
    }

    public Shipment patch(Long invoiceId, Long shipmentId, Shipment update) {
        Validator validator = this.utilProvider.getValidator();
        validator.rule(update != null, "Shipment cannot be null");
        validator.raiseErrors();

        Shipment existing = repo.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment", shipmentId));

        List<ShipmentItem> existingItems = existing.getItems();
        List<ShipmentItem> updatedItems = itemService.getPatchItems(existingItems, update.getItems());
        existing.outerJoin(update, getPropertyNames(UpdateShipment.class));
        existing.setItems(updatedItems);

        return repo.save(invoiceId, existing);
    }
}
