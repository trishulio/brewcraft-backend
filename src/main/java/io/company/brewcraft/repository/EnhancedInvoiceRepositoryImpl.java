package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;

public class EnhancedInvoiceRepositoryImpl implements EnhancedInvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceRepositoryImpl.class);

    private InvoiceItemRepository itemRepo;
    private InvoiceStatusRepository statusRepo;
    private PurchaseOrderRepository poRepo;

    @Autowired
    public EnhancedInvoiceRepositoryImpl(InvoiceItemRepository itemRepo, InvoiceStatusRepository statusRepo, PurchaseOrderRepository poRepo) {
        this.itemRepo = itemRepo;
        this.statusRepo = statusRepo;
        this.poRepo = poRepo;
    }

    @Override
    public void refresh(Collection<Invoice> invoices) {
        poRepo.refreshAccessors(invoices);
        statusRepo.refreshAccessors(invoices);

        List<InvoiceItem> items = invoices.stream().filter(i -> i.getItems() != null).flatMap(i -> i.getItems().stream()).collect(Collectors.toList());
        itemRepo.refresh(items);
    }
}
