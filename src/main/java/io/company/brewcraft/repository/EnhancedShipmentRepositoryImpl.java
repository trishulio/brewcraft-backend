package io.company.brewcraft.repository;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedShipmentRepositoryImpl implements EnhancedShipmentRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedShipmentRepositoryImpl.class);

    private ShipmentRepository shipmentRepo;
    private ShipmentStatusRepository statusRepo;
    private InvoiceRepository invoiceRepo;
    private MaterialRepository materialRepo;

    @Autowired
    public EnhancedShipmentRepositoryImpl(ShipmentRepository shipmentRepo, ShipmentStatusRepository statusRepo, InvoiceRepository invoiceRepo, MaterialRepository materialRepo) {
        this.shipmentRepo = shipmentRepo;
        this.statusRepo = statusRepo;
        this.invoiceRepo = invoiceRepo;
        this.materialRepo = materialRepo;
    }

    @Override
    public Shipment save(Long invoiceId, Shipment shipment) {
        log.debug("Attempting to save shipment with Id: {} under the invoiceId: {}", shipment.getId(), invoiceId);
        Invoice invoice = null;

        if (invoiceId != null) {
            invoice = this.invoiceRepo.findById(invoiceId).orElseThrow(() -> new EntityNotFoundException("Invoice", invoiceId));
        }
        shipment.setInvoice(invoice);

        String statusName = ShipmentStatus.DEFAULT_STATUS;
        if (shipment.getStatus() != null && shipment.getStatus().getName() != null) {
            statusName = shipment.getStatus().getName();
        }
        log.debug("Shipment status name: {}", statusName);
        Iterator<ShipmentStatus> it = statusRepo.findByNames(Set.of(statusName)).iterator();
        if (!it.hasNext()) {
            log.error("ShipmentStatus not found for name: {}", statusName);
            throw new EntityNotFoundException("ShipmentStatus", "name", statusName);
        }
        shipment.setStatus(it.next());

        if (shipment.getItems() != null) {
            Set<Long> materialIds = shipment.getItems().stream().filter(i -> i != null && i.getMaterial() != null && i.getMaterial().getId() != null).map(i -> i.getMaterial().getId()).collect(Collectors.toSet());
            log.debug("Asserting that the following material Ids refer to existing materials: {}", materialIds);
            if (!materialIds.isEmpty() && !materialRepo.existsByIds(materialIds)) {
                throw new EntityNotFoundException("Materials", "ids", materialIds.toString());
            }
        }

        log.debug("Saving shipment");
        return this.shipmentRepo.save(shipment);
    }

}
