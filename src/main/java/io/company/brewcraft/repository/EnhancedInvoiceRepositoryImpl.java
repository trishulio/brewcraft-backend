package io.company.brewcraft.repository;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceStatus;
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
        log.info("Invoice with Id: {} has {} items and belong to PurchaseOrder: {}", invoice.getId(), invoice.getItems() != null ? invoice.getItems().size() : null, invoice.getPurchaseOrder() != null ? invoice.getPurchaseOrder().getId() : null);
        log.info("Attempting to fetch PurchaseOrder with Id: {}", purchaseOrderId);
        PurchaseOrder po = poRepo.findById(purchaseOrderId).orElse(null);
        invoice.setPurchaseOrder(po);

        String statusName = InvoiceStatus.DEFAULT_STATUS_NAME;
        if (invoice.getStatus() != null && invoice.getStatus().getName() != null) {
            statusName = invoice.getStatus().getName();
        }
        log.info("Target Invoice Status Name: {}", statusName);

        Iterator<InvoiceStatus> it = statusRepo.findByNames(Set.of(statusName)).iterator();
        if (!it.hasNext()) {
            log.error("ShipmentStatus not found for name: {}", statusName);
            throw new EntityNotFoundException("ShipmentStatus", "name", statusName);
        }
        invoice.setStatus(it.next());

        return invoiceRepo.saveAndFlush(invoice);
    }
}
