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

public class EnhancedInvoiceRepositoryImpl implements EnhancedInvoiceRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceRepositoryImpl.class);

    private final InvoiceItemRepository itemRepo;
    private final InvoiceStatusRepository statusRepo;
    private final PurchaseOrderRepository poRepo;

    @Autowired
    public EnhancedInvoiceRepositoryImpl(InvoiceItemRepository itemRepo, InvoiceStatusRepository statusRepo, PurchaseOrderRepository poRepo) {
        this.itemRepo = itemRepo;
        this.statusRepo = statusRepo;
        this.poRepo = poRepo;
    }

    @Override
    public void refresh(Collection<Invoice> invoices) {
        this.poRepo.refreshAccessors(invoices);
        this.statusRepo.refreshAccessors(invoices);

        final List<InvoiceItem> items = invoices.stream().filter(i -> i.getItems() != null).flatMap(i -> i.getItems().stream()).collect(Collectors.toList());
        this.itemRepo.refresh(items);
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceAccessor> accessors) {
        throw new NoSuchMethodError("Not yet implemented");
    }
}
