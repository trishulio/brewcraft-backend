package io.company.brewcraft.dto;

import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateInvoice<T extends UpdateInvoiceItem<? extends UpdateInvoice<T>>> extends BaseInvoice<T>, UpdatableEntity<Long> {
}
