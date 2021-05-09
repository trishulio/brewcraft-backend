package io.company.brewcraft.model;

import io.company.brewcraft.dto.UpdateInvoice;

public interface UpdateInvoiceItem<T extends UpdateInvoice<? extends UpdateInvoiceItem<T>>> extends Identified<Long>, BaseInvoiceItem<T>, Versioned {
}
