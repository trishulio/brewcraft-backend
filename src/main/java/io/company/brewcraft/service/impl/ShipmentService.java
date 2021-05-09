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
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
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
    private MaterialLotService lotService;

    public ShipmentService(ShipmentRepository repo, MaterialLotService lotService) {
        this.repo = repo;
        this.lotService = lotService;
    }

    public Page<Shipment> getShipments(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> shipmentNumbers,
        Set<String> descriptions,
        Set<Long> statusIds,
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
                                                        .in(Shipment.FIELD_DESCRIPTION, descriptions)
                                                        .in(new String[] {Shipment.FIELD_STATUS, ShipmentStatus.FIELD_ID}, statusIds)
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

    public Shipment add(Shipment shipment) {
        log.debug("Adding a new shipment with {} lots", shipment.getLots() == null ? null : shipment.getLots().size());
        Shipment addition = new Shipment();
        List<MaterialLot> lotAdditions = lotService.getAddLots(shipment.getLots());
        log.debug("Number of lot additions: {}", lotAdditions == null ? null : lotAdditions.size());

        addition.override(shipment, getPropertyNames(BaseShipment.class));
        addition.setLots(lotAdditions);

        repo.refresh(List.of(addition));

        return repo.saveAndFlush(addition);
    }

    public Shipment put(Long shipmentId, Shipment update) {
        log.debug("Putting a shipment with Id: {}", shipmentId);

        Shipment existing = getShipment(shipmentId);
        Class<? super Shipment> shipmentClz = BaseShipment.class;

        if (existing == null) {
            existing = new Shipment(shipmentId); //TODO: Hibernate ignores the id.
        } else {
            existing.optimisticLockCheck(update);
            shipmentClz = UpdateShipment.class;
        }

        log.debug("Shipment with Id: {} has {} existing lots", existing == null ? null : shipmentId, existing.getLots() == null ? null : existing.getLots().size());
        log.debug("Update payload has {} lot updates", update.getLots() == null ? null : update.getLots().size());

        List<MaterialLot> updatedLots = lotService.getPutLots(existing.getLots(), update.getLots());
        log.debug("Total UpdateLots: {}", updatedLots == null ? null : updatedLots.size());

        Shipment temp = new Shipment();
        temp.override(update, getPropertyNames(shipmentClz));
        temp.setLots(updatedLots);
        repo.refresh(List.of(temp));

        existing.override(temp, getPropertyNames(shipmentClz));

        return repo.saveAndFlush(existing);
    }

    public Shipment patch(Long shipmentId, Shipment update) {
        log.debug("Performing patch on Shipment with Id: {}", shipmentId);

        Shipment existing = repo.findById(shipmentId).orElseThrow(() -> new EntityNotFoundException("Shipment", shipmentId));
        existing.optimisticLockCheck(update);

        List<MaterialLot> existingLots = existing.getLots();
        List<MaterialLot> updatedLots = lotService.getPatchLots(existingLots, update.getLots());

        Shipment temp = new Shipment();
        temp.override(existing);
        temp.outerJoin(update, getPropertyNames(UpdateShipment.class));
        temp.setLots(updatedLots);        
        repo.refresh(List.of(temp));

        existing.override(temp, getPropertyNames(UpdateShipment.class));

        return repo.saveAndFlush(existing);
    }
}
