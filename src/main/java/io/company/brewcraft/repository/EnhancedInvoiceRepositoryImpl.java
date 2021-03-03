package io.company.brewcraft.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.PurchaseOrderEntity;
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
    public InvoiceEntity refreshAndAdd(Long purchaseOrderId, InvoiceEntity invoice) {
        log.info("Invoice with Id: {} has {} items and belong to PurchaseOrder: {}", invoice.getId(), invoice.getItems() != null ? invoice.getItems().size() : null, invoice.getPurchaseOrder() != null ? invoice.getPurchaseOrder().getId() : null);
        log.info("Attempting to fetch PurchaseOrder with Id: {}", purchaseOrderId);
        PurchaseOrderEntity po = poRepo.findById(purchaseOrderId).orElseThrow(() -> new EntityNotFoundException("PurchaseOrder", purchaseOrderId));

        String statusName = invoice.getStatus() != null ? invoice.getStatus().getName() : null;
        statusName = statusName != null ? statusName : InvoiceStatusEntity.DEFAULT_STATUS_NAME;
        log.info("Target Invoice Status Name: {}", statusName);
        final String finalStatusName = statusName;
        InvoiceStatusEntity status = statusRepo.findByName(statusName).orElseThrow(() -> new EntityNotFoundException("InvoiceStatus", "name", finalStatusName));

        Map<Long, List<InvoiceItemEntity>> materialToItems = invoice.getItems().stream().filter(item -> item.getMaterial() != null && item.getMaterial().getId() != null).collect(Collectors.groupingBy(item -> item.getMaterial().getId()));
        log.info("Material to Items Mapping: {}", materialToItems);

        List<MaterialEntity> materials = materialRepo.findAllById(materialToItems.keySet());
        log.info("Total materials fetched: {}", materials.size());

        materials.forEach(material -> materialToItems.get(material.getId()).forEach(item -> item.setMaterial(material)));

        invoice.setPurchaseOrder(po);
        invoice.setStatus(status);

        return invoiceRepo.saveAndFlush(invoice);
    }
}
