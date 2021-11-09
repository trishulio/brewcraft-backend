package io.company.brewcraft.model.procurement;

import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateProcurementItem<I extends UpdateInvoice<? extends UpdateInvoiceItem<I>>, S extends UpdateShipment<? extends UpdateMaterialLot<S>>, P extends UpdateProcurement<? extends UpdateInvoiceItem<I>, ? extends UpdateMaterialLot<S>, ? extends UpdateProcurementItem<I, S, P>>> extends BaseProcurementItem<I, S, P>, UpdatableEntity<ProcurementItemId> {
}
