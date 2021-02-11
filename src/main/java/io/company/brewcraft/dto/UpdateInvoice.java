package io.company.brewcraft.dto;

import io.company.brewcraft.model.Versioned;
import io.company.brewcraft.pojo.UpdateInvoiceItem;

public interface UpdateInvoice<T extends UpdateInvoiceItem> extends BaseInvoice<T>, Versioned {
}
