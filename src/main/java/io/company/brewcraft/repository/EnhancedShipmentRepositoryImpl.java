package io.company.brewcraft.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedShipmentRepositoryImpl implements EnhancedShipmentRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedShipmentRepositoryImpl.class);

    private ShipmentStatusRepository statusRepo;
    private InvoiceRepository invoiceRepo;
    private MaterialRepository materialRepo;

    @Autowired
    public EnhancedShipmentRepositoryImpl(ShipmentStatusRepository statusRepo, InvoiceRepository invoiceRepo, MaterialRepository materialRepo) {
        this.statusRepo = statusRepo;
        this.invoiceRepo = invoiceRepo;
        this.materialRepo = materialRepo;
    }

    @Override
    public void refresh(Long invoiceId, Shipment shipment) {
        log.debug("Attempting to save shipment with Id: {} under the invoiceId: {}", shipment.getId(), invoiceId);
        Invoice invoice = null;

        if (invoiceId != null) {
            invoice = this.invoiceRepo.findById(invoiceId).orElseThrow(() -> new EntityNotFoundException("Invoice", invoiceId));
        }
        shipment.setInvoice(invoice);

        String statusName = ShipmentStatus.DEFAULT_STATUS;
        if (shipment.getStatus() != null && shipment.getStatus().getName() != null) {
            log.debug("Using specified shipment status");
            statusName = shipment.getStatus().getName();
        }
        log.debug("Shipment status name: {}", statusName);
        final String targetStatusName = statusName.toString();
        ShipmentStatus status = statusRepo.findByName(targetStatusName).orElseThrow(() -> new EntityNotFoundException("ShipmentStatus", "name", targetStatusName));
        log.debug("Shipment status fetched from repository with name: {}", status.getName());
        shipment.setStatus(status);

        if (shipment.getLots() != null && shipment.getLots().size() > 0) {
            Map<Long, List<MaterialLot>> materialIdToLotLookup = shipment.getLots().stream().filter(i -> i != null && i.getMaterial() != null).collect(Collectors.groupingBy(lot -> lot.getMaterial().getId()));
            log.debug("Material to Lots Mapping: {}", materialIdToLotLookup);

            List<Material> materials = materialRepo.findAllById(materialIdToLotLookup.keySet());
            log.debug("Total materials fetched: {}", materials.size());

            if (materialIdToLotLookup.keySet().size() != materials.size()) {
                List<Long> materialIds = materials.stream().map(material -> material.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all materials in Id-Set: %s. Materials found with Ids: %s", materialIdToLotLookup.keySet(), materialIds));
            }

            materials.forEach(material -> materialIdToLotLookup.get(material.getId()).forEach(lot -> lot.setMaterial(material)));
        }
    }
}
