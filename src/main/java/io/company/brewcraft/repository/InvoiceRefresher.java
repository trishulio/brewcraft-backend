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
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public class InvoiceRefresher implements Refresher<Invoice, InvoiceAccessor> {
    private static final Logger log = LoggerFactory.getLogger(InvoiceRefresher.class);

    private final AccessorRefresher<Long, InvoiceAccessor, Invoice> refresher;

    private final Refresher<InvoiceItem, InvoiceItemAccessor> invoiceItemRefresher;
    private final Refresher<InvoiceStatus, InvoiceStatusAccessor> invoiceStatusRefresher;
    private final Refresher<PurchaseOrder, PurchaseOrderAccessor> poRefresher;

    public InvoiceRefresher(AccessorRefresher<Long, InvoiceAccessor, Invoice> refresher, Refresher<InvoiceItem, InvoiceItemAccessor> invoiceItemRefresher, Refresher<InvoiceStatus, InvoiceStatusAccessor> invoiceStatusRefresher, Refresher<PurchaseOrder, PurchaseOrderAccessor> poRefresher) {
        this.refresher = refresher;
        this.invoiceItemRefresher = invoiceItemRefresher;
        this.invoiceStatusRefresher = invoiceStatusRefresher;
        this.poRefresher = poRefresher;
    }

    @Override
    public void refresh(Collection<Invoice> invoices) {
        this.poRefresher.refreshAccessors(invoices);
        this.invoiceStatusRefresher.refreshAccessors(invoices);

        final List<InvoiceItem> invoiceItems = invoices == null ? null : invoices.stream().filter(i -> i != null && i.getItemCount() > 0).flatMap(i -> i.getInvoiceItems().stream()).collect(Collectors.toList());
        this.invoiceItemRefresher.refresh(invoiceItems);
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
