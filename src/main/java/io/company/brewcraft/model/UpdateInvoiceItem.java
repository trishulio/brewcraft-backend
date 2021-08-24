package io.company.brewcraft.model;

import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateInvoiceItem<T extends UpdateInvoice<? extends UpdateInvoiceItem<T>>> extends BaseInvoiceItem<T>, UpdatableEntity<Long> {
}
