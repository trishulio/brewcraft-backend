package io.company.brewcraft.repository;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;

public interface EnhancedInvoiceRepository extends Refresher<Invoice, InvoiceAccessor>{
}
