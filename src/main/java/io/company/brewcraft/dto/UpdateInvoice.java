package io.company.brewcraft.dto;

import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.Versioned;

public interface UpdateInvoice<T extends UpdateInvoiceItem> extends BaseInvoice<T>, Versioned {
}
