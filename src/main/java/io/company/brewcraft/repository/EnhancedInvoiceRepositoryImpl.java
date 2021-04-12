package io.company.brewcraft.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedInvoiceRepositoryImpl implements EnhancedInvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceRepositoryImpl.class);

    private InvoiceRepository invoiceRepo;
    private InvoiceStatusRepository statusRepo;
    private PurchaseOrderRepository poRepo;
    private MaterialRepository materialRepo;

    @Autowired
    public EnhancedInvoiceRepositoryImpl(InvoiceRepository invoiceRepo, InvoiceStatusRepository statusRepo, PurchaseOrderRepository poRepo, MaterialRepository materialRepo) {
        this.invoiceRepo = invoiceRepo;
        this.statusRepo = statusRepo;
        this.poRepo = poRepo;
        this.materialRepo = materialRepo;
    }

    @Override
    public Invoice save(Long purchaseOrderId, Invoice invoice) {
        log.debug("Invoice with Id: {} has {} items and belong to PurchaseOrder: {}", invoice.getId(), invoice.getItems() != null ? invoice.getItems().size() : null, invoice.getPurchaseOrder() != null ? invoice.getPurchaseOrder().getId() : null);
        log.debug("Attempting to fetch PurchaseOrder with Id: {}", purchaseOrderId);
        PurchaseOrder po = null;
        if (purchaseOrderId != null) {            
            po = poRepo.findById(purchaseOrderId).orElse(null);
        }
        invoice.setPurchaseOrder(po);

        String statusName = InvoiceStatus.DEFAULT_STATUS_NAME;
        if (invoice.getStatus() != null && invoice.getStatus().getName() != null) {
            statusName = invoice.getStatus().getName();
        }
        log.debug("Target Invoice Status Name: {}", statusName);

        final String targetStatusName = statusName;
        InvoiceStatus status = statusRepo.findByName(statusName).orElseThrow(() -> new EntityNotFoundException("InvoiceStatus", "name", targetStatusName));
        invoice.setStatus(status);

        if (invoice.getItems() != null && invoice.getItems().size() > 0) {
            Map<Long, List<InvoiceItem>> materialToItems = invoice.getItems().stream().filter(item -> item != null && item.getMaterial() != null).collect(Collectors.groupingBy(item -> item.getMaterial().getId()));
            log.debug("Material to Items Mapping: {}", materialToItems);

            List<Material> materials = materialRepo.findAllById(materialToItems.keySet());
            log.debug("Total materials fetched: {}", materials.size());

            if (materialToItems.keySet().size() != materials.size()) {
                List<Long> materialIds = materials.stream().map(material -> material.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all materials in Id-Set: %s. Materials found with Ids: %s", materialToItems.keySet(), materialIds));
            }

            invoice.getItems().forEach(i -> i.setMaterial((Material) null));
            materials.forEach(material -> materialToItems.get(material.getId()).forEach(item -> item.setMaterial(material)));            
        }

        return invoiceRepo.saveAndFlush(invoice);
    }
}
