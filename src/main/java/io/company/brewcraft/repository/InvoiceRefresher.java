package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;

public class InvoiceRefresher implements EnhancedInvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(InvoiceRefresher.class);

    private final AccessorRefresher<Long, InvoiceAccessor, Invoice> refresher;

    private final InvoiceItemRepository invoiceItemRepo;
    private final InvoiceStatusRepository invoiceStatusRepo;
    private final PurchaseOrderRepository poRepo;

    @Autowired
    public InvoiceRefresher(AccessorRefresher<Long, InvoiceAccessor, Invoice> refresher, InvoiceItemRepository invoiceItemRepo, InvoiceStatusRepository invoiceStatusRepo, PurchaseOrderRepository poRepo) {
        this.refresher = refresher;
        this.invoiceItemRepo = invoiceItemRepo;
        this.invoiceStatusRepo = invoiceStatusRepo;
        this.poRepo = poRepo;
    }

    @Override
    public void refresh(Collection<Invoice> invoices) {
        this.poRepo.refreshAccessors(invoices);
        this.invoiceStatusRepo.refreshAccessors(invoices);

        final List<InvoiceItem> invoiceItems = invoices == null ? null : invoices.stream().filter(i -> i != null && i.getItemCount() > 0).flatMap(i -> i.getInvoiceItems().stream()).collect(Collectors.toList());
        this.invoiceItemRepo.refresh(invoiceItems);
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
